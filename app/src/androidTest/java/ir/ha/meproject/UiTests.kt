package ir.ha.meproject

import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import ir.ha.meproject.common.espresso_util.IdlingResourcesKeys
import ir.ha.meproject.common.espresso_util.MyCountingIdlingResource
import ir.ha.meproject.common.espresso_util.MyIdlingResource
import ir.ha.meproject.common.espresso_util.getIdlingResource
import ir.ha.meproject.di.NetworkModule
import ir.ha.meproject.helper.MockWebServerDispatcher
import ir.ha.meproject.presentation.MainActivity
import ir.ha.meproject.presentation.features.fragments.home.HomeFragment
import ir.ha.meproject.presentation.features.fragments.more.MoreFragment
import ir.ha.meproject.presentation.features.fragments.more.MoreFragmentArgs
import ir.ha.meproject.presentation.features.fragments.splash.SplashFragment
import ir.ha.meproject.presentation.test_activity.TestActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@UninstallModules(NetworkModule::class)
@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class UiTests {

    val TAG = this::class.java.simpleName

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var mockWebServer: MockWebServer

    private val mockWebServerDispatcher = MockWebServerDispatcher()

    @Before
    fun setup() {
        Log.i(TAG, "setup: ")
        hiltRule.inject()
        mockWebServer.start(8080)
    }

    @After
    fun teardown() {
        Log.i(TAG, "teardown: ")
        mockWebServer.shutdown()
    }

    @Test
    fun integration_between_fragments_TEST() {
        Log.i(
            TAG,
            "check_navigate_and_scenario_from_splash_to_lastFragment_is_correct_or_no_by_counting_TEST: "
        )

        var idleResources: MyCountingIdlingResource? = null
        val activityScenarioRule = ActivityScenario.launch(MainActivity::class.java)

        activityScenarioRule.onActivity { activity ->

            val navHostFragment = activity.supportFragmentManager.primaryNavigationFragment
            val splashFragment =
                navHostFragment?.childFragmentManager?.fragments?.find { it is SplashFragment } as? SplashFragment

            if (splashFragment != null) {
                idleResources =
                    getIdlingResource(IdlingResourcesKeys.SPLASH) as MyCountingIdlingResource
                IdlingRegistry.getInstance().register(idleResources)
            }
        }

        onView(withId(R.id.goToMoreFragment)).check(matches(isDisplayed()))
        onView(withId(R.id.goToMoreFragment)).perform(click())
        IdlingRegistry.getInstance().unregister(idleResources)
        activityScenarioRule.close()
    }

    @Test
    fun integration_between_splash_api_call_happen_successfully() {


        var idleResources: MyCountingIdlingResource? = null

        mockWebServer.dispatcher = mockWebServerDispatcher.RequestDispatcher()
        val activityScenarioRule = ActivityScenario.launch(MainActivity::class.java)

        activityScenarioRule.onActivity { activity ->

            val navHostFragment = activity.supportFragmentManager.primaryNavigationFragment
            val splashFragment =
                navHostFragment?.childFragmentManager?.fragments?.find { it is SplashFragment } as? SplashFragment

            if (splashFragment != null) {
                idleResources =
                    getIdlingResource(IdlingResourcesKeys.SPLASH) as MyCountingIdlingResource
                IdlingRegistry.getInstance().register(idleResources)
            }
        }

        onView(withId(R.id.tv)).check(matches(withText("Error")))
        IdlingRegistry.getInstance().unregister(idleResources)
        activityScenarioRule.close()
    }


    @Test
    fun integration_between_splash_api_call_happen_error() {
        var idleResources: MyCountingIdlingResource? = null

        mockWebServer.dispatcher = MockWebServerDispatcher().ErrorDispatcher()
        val activityScenarioRule = ActivityScenario.launch(MainActivity::class.java)

        activityScenarioRule.onActivity { activity ->
            val navHostFragment = activity.supportFragmentManager.primaryNavigationFragment
            val splashFragment =
                navHostFragment?.childFragmentManager?.fragments?.find { it is SplashFragment } as? SplashFragment

            if (splashFragment != null) {
                idleResources =
                    getIdlingResource(IdlingResourcesKeys.SPLASH) as MyCountingIdlingResource
                IdlingRegistry.getInstance().register(idleResources)
            } else {
                Log.e("Test", "SplashFragment not found")
            }
        }

        Log.d("Test", "Performing view assertions for error display")
        onView(withId(R.id.tv)).check(matches(isDisplayed()))
        onView(withId(R.id.tv)).check(matches(withText("Error")))

        // Unregister IdlingResource after the test
        IdlingRegistry.getInstance().unregister(idleResources)
        activityScenarioRule.close()
    }


    @Test
    fun launch_more_fragment_and_check_it_argument_TEST() {

        val myIdlingResource = MyIdlingResource(IdlingResourcesKeys.SPLASH.name).apply {
            setIdleState(false)
            IdlingRegistry.getInstance().register(this)
        }

        val sampleIntentData = "sample intent data"
        val argumentValue = "Sample Argument"
        val args = MoreFragmentArgs(argumentValue).toBundle()

        val intent = Intent(ApplicationProvider.getApplicationContext(), TestActivity::class.java)
        intent.putExtra("key", sampleIntentData)

        val activityScenario = ActivityScenario.launch<TestActivity>(intent)
        activityScenario.onActivity { activity ->
            val fragment = MoreFragment().apply {
                arguments = args
            }
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitNow()
        }
        myIdlingResource.setIdleState(true)
        onView(withId(R.id.argumentsTV)).check(matches(withText(argumentValue)))
        onView(withId(R.id.intentValueTV)).check(matches(withText(sampleIntentData)))
        IdlingRegistry.getInstance().unregister(myIdlingResource)
        activityScenario.close()
    }

}
