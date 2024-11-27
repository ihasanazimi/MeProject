package ir.ha.meproject.samples

import android.content.Intent
import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import ir.ha.meproject.R
import ir.ha.meproject.common.espresso_util.ManualCheckingIdlingResource
import ir.ha.meproject.di.NetworkModule
import ir.ha.meproject.helper.MockWebServerDispatcher
import ir.ha.meproject.presentation.activities.main.MainActivity
import ir.ha.meproject.presentation.activities.test.TestActivity
import ir.ha.meproject.presentation.adapters.UsersAdapter
import ir.ha.meproject.presentation.fragments.features.home.HomeFragment
import ir.ha.meproject.presentation.fragments.features.more.MoreFragment
import ir.ha.meproject.presentation.fragments.features.more.MoreFragmentArgs
import ir.ha.meproject.presentation.fragments.features.splash.SplashFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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

    @Inject
    lateinit var mockWebServerDispatcher : MockWebServerDispatcher

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
    fun integrationBetweenFragments() {
        Log.i(TAG, "integrationBetweenFragments")

        mockWebServer.dispatcher = mockWebServerDispatcher.RequestDispatcher()
        val idleResources = ManualCheckingIdlingResource(HomeFragment::class.java.simpleName)
        idleResources.setIdleState(false)

        val activityScenarioRule = ActivityScenario.launch(MainActivity::class.java)
        activityScenarioRule.onActivity { activity ->
            val navHostFragment = activity.supportFragmentManager.primaryNavigationFragment
            val splashFragment = navHostFragment?.childFragmentManager?.fragments?.find { it is SplashFragment } as? SplashFragment
            if (splashFragment != null) {
                IdlingRegistry.getInstance().register(idleResources)
            }
        }

        onView(withId(R.id.goToMoreFragment)).perform(click())

        onView(withId(R.id.goToUsersFragment)).perform(click())

        onView(ViewMatchers.withId(R.id.recyclerView)).perform(
            RecyclerViewActions.scrollTo<UsersAdapter.VH>(
                hasDescendant(withText("Oliver - Chen"))
            )
        )

        idleResources.setIdleState(true)
        IdlingRegistry.getInstance().unregister(idleResources)
        activityScenarioRule.close()
    }

    @Test
    fun splashErrorStatus() {

        Log.i(TAG, "splashErrorStatus: ")
        val idleResources = ManualCheckingIdlingResource(HomeFragment::class.java.simpleName)
        idleResources.setIdleState(false)
        mockWebServer.dispatcher = mockWebServerDispatcher.ErrorDispatcher()
        val activityScenarioRule = ActivityScenario.launch(MainActivity::class.java)

        activityScenarioRule.onActivity { activity ->

            val navHostFragment = activity.supportFragmentManager.primaryNavigationFragment
            val splashFragment =
                navHostFragment?.childFragmentManager?.fragments?.find { it is SplashFragment } as? SplashFragment

            if (splashFragment != null) {
                IdlingRegistry.getInstance().register(idleResources)
            }
        }
        idleResources.setIdleState(true)

        onView(withId(R.id.tv)).check(matches(withText("Error")))
        IdlingRegistry.getInstance().unregister(idleResources)
        activityScenarioRule.close()
    }

    @Test
    fun launch_moreFragment_and_check_it_Argument() {

        val manualCheckingIdlingResource = ManualCheckingIdlingResource(HomeFragment::class.java.simpleName).apply {
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
        manualCheckingIdlingResource.setIdleState(true)
        onView(withId(R.id.argumentsTV)).check(matches(withText(argumentValue)))
        onView(withId(R.id.intentValueTV)).check(matches(withText(sampleIntentData)))
        IdlingRegistry.getInstance().unregister(manualCheckingIdlingResource)
        activityScenario.close()
    }

}
