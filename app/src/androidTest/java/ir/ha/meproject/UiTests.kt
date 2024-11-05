package ir.ha.meproject

import android.os.StrictMode
import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import ir.ha.meproject.common.espresso_util.IdlingResourcesKeys
import ir.ha.meproject.common.espresso_util.MyCountingIdlingResource
import ir.ha.meproject.common.espresso_util.MyIdlingResource
import ir.ha.meproject.common.espresso_util.getIdlingResource
import ir.ha.meproject.data.remote.ApiServices
import ir.ha.meproject.presentation.MainActivity
import ir.ha.meproject.presentation.features.fragments.more.MoreFragment
import ir.ha.meproject.presentation.features.fragments.more.MoreFragmentArgs
import ir.ha.meproject.presentation.features.fragments.splash.SplashFragment
import ir.ha.meproject.presentation.test_activity.TestActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@MediumTest
class UiTests {

    val TAG = this::class.java.simpleName

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        Log.i(TAG, "setup: ")
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
            .permitAll()
            .build())
        hiltRule.inject()
    }

    @After
    fun teardown() {
        Log.i(TAG, "teardown: ")
        mockWebServer.shutdown()
    }

    @Test
    fun integration_between_fragments_TEST() {
        Log.i(TAG, "check_navigate_and_scenario_from_splash_to_lastFragment_is_correct_or_no_by_counting_TEST: ")

        var idleResources: MyCountingIdlingResource? = null
        val activityScenarioRule = ActivityScenario.launch(MainActivity::class.java)

        activityScenarioRule.onActivity { activity ->

            val navHostFragment = activity.supportFragmentManager.primaryNavigationFragment
            val splashFragment = navHostFragment?.childFragmentManager?.fragments?.find { it is SplashFragment } as? SplashFragment

            if (splashFragment != null) {
                idleResources = getIdlingResource(IdlingResourcesKeys.SPLASH) as MyCountingIdlingResource
                IdlingRegistry.getInstance().register(idleResources)
            }
        }

        onView(withId(R.id.goToNextPage)).check(matches(isDisplayed()))
        onView(withId(R.id.goToNextPage)).perform(click())
        IdlingRegistry.getInstance().unregister(idleResources)
        activityScenarioRule.close()
    }

    @Test
    fun integration_between_fragments_TEST_2() {
        Log.i(TAG, "check_navigate_and_scenario_from_splash_to_lastFragment_is_correct_or_no_by_counting_TEST: ")


        val mockResponse = MockResponse()
            .setResponseCode(404)
            .setBody("{\"error\": \"Not Found\"}")
        mockWebServer.enqueue(mockResponse)

        var idleResources: MyCountingIdlingResource? = null
        val activityScenarioRule = ActivityScenario.launch(MainActivity::class.java)

        activityScenarioRule.onActivity { activity ->

            val navHostFragment = activity.supportFragmentManager.primaryNavigationFragment
            val splashFragment = navHostFragment?.childFragmentManager?.fragments?.find { it is SplashFragment } as? SplashFragment

            if (splashFragment != null) {
                idleResources = getIdlingResource(IdlingResourcesKeys.SPLASH) as MyCountingIdlingResource
                IdlingRegistry.getInstance().register(idleResources)
            }
        }

        onView(withId(R.id.goToNextPage)).check(matches(isDisplayed()))
        onView(withId(R.id.goToNextPage)).perform(click())
        IdlingRegistry.getInstance().unregister(idleResources)
        activityScenarioRule.close()
    }

    @Test
    fun launch_more_fragment_and_check_it_argument_TEST() {

        val myIdlingResource = MyIdlingResource("launch_more_fragment_and_check_argument").apply {
            setIdleState(false)
            IdlingRegistry.getInstance().register(this)
        }

        val argumentValue = "Sample Argument"
        val args = MoreFragmentArgs(argumentValue).toBundle()

        val activityScenario = ActivityScenario.launch(TestActivity::class.java)
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
        IdlingRegistry.getInstance().unregister(myIdlingResource)
        activityScenario.close()
    }

}
