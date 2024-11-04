package ir.ha.meproject

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.MediumTest
import ir.ha.meproject.common.espresso_util.MyCountingIdlingResource
import ir.ha.meproject.common.espresso_util.MyIdlingResource
import ir.ha.meproject.presentation.MainActivity
import ir.ha.meproject.presentation.features.fragments.splash.SplashFragment
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
class UiTests {

    val TAG = this::class.java.simpleName

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        Log.i(TAG, "setup: ")
    }

    @After
    fun tearDown() {
        Log.i(TAG, "tearDown: ")
    }

    @Test
    fun check_navigate_and_scenario_from_splash_to_lastFragment_is_correct_or_no_by_simpleIdleResource_TEST() {
        Log.i(
            TAG,
            "check_navigate_and_scenario_from_splash_to_lastFragment_is_correct_or_no_by_simpleIdleResource_TEST: "
        )

        var idleResources: MyIdlingResource?= null
        activityScenarioRule.scenario.onActivity { activity ->
            val navHostFragment = activity.supportFragmentManager.primaryNavigationFragment
            val splashFragment = navHostFragment?.childFragmentManager?.fragments?.find { it is SplashFragment } as? SplashFragment
            if (splashFragment != null) {
                idleResources = splashFragment.getIdlingResource()
                IdlingRegistry.getInstance().register(idleResources)
            }
        }

        onView(withId(R.id.goToNextPage)).check(matches(isDisplayed()))
        onView(withId(R.id.goToNextPage)).perform(click())
        IdlingRegistry.getInstance().unregister(idleResources)
    }



    @Test
    fun check_navigate_and_scenario_from_splash_to_lastFragment_is_correct_or_no_by_counting_TEST() {
        Log.i(
            TAG,
            "check_navigate_and_scenario_from_splash_to_lastFragment_is_correct_or_no_by_counting_TEST: "
        )

        var idleResources: MyCountingIdlingResource?= null
        activityScenarioRule.scenario.onActivity { activity ->
            val navHostFragment = activity.supportFragmentManager.primaryNavigationFragment
            val splashFragment = navHostFragment?.childFragmentManager?.fragments?.find { it is SplashFragment } as? SplashFragment
            if (splashFragment != null) {
                idleResources = splashFragment.getMyCountingIdlingResource()
                IdlingRegistry.getInstance().register(idleResources)
            }
        }

        onView(withId(R.id.goToNextPage)).check(matches(isDisplayed()))
        onView(withId(R.id.goToNextPage)).perform(click())
        IdlingRegistry.getInstance().unregister(idleResources)
    }



    @Test
    fun doSomething2() {
        Log.i(TAG, "doSomething: ")
        onView(withId(R.id.goToNextPage)).check(matches(isDisplayed()))
        onView(withId(R.id.goToNextPage)).perform(click())
    }
}
