package id.xxx.submission.ui.main

import android.app.Activity
import android.content.Context
import androidx.navigation.findNavController
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import id.xxx.submission.R
import id.xxx.submission.R.id.nav_host_main_fragment
import id.xxx.submission.utils.EspressoIdlingResource.espressoTestIdlingResource
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    private val ctx = getApplicationContext<Context>()
    private val currentDes =
        { activity: Activity -> activity.findNavController(nav_host_main_fragment).currentDestination }

    @get:Rule
    val asr = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(espressoTestIdlingResource)
    }

    @Test
    fun main_activity_loaded() {
        asr.scenario.onActivity { activity ->
            assertEquals(ctx.getString(R.string.movies), currentDes(activity)?.label)
        }

        onView(withId(R.id.bottom_navigation_view)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_recycler_view)).check(matches(isDisplayed()))

        onView(withId(R.id.menu_sort_by_size)).check(matches(isDisplayed()))
        onView(withId(R.id.menu_sort_by_size)).perform(click())
        onView(withText(R.string.by_release)).check(matches(isDisplayed()))
        onView(withText(R.string.by_name)).check(matches(isDisplayed()))
        onView(withText(R.string.by_release)).perform(click())
    }

    @Test
    fun bottom_navigation_move_to_tv() {
        onView(withId(R.id.fragment_tv)).perform(click())
        asr.scenario.onActivity {
            assertEquals(ctx.getString(R.string.tv_show), currentDes(it)?.label)
        }
        onView(withId(R.id.fragment_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_recycler_view)).check(matches(isDisplayed()))

        onView(withId(R.id.menu_sort_by_size)).check(matches(isDisplayed()))
        onView(withId(R.id.menu_sort_by_size)).perform(click())
        onView(withText(R.string.by_release)).check(matches(isDisplayed()))
        onView(withText(R.string.by_name)).check(matches(isDisplayed()))
        onView(withText(R.string.by_release)).perform(click())
    }

    @Test
    fun bottom_navigation_move_to_favorite() {
        onView(withId(R.id.fragment_favorite)).perform(click())
        asr.scenario.onActivity {
            assertEquals(ctx.getString(R.string.favorite), currentDes(it)?.label)
        }
        onView(withId(R.id.fragment_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_recycler_view)).check(matches(isDisplayed()))

        onView(withText(R.string.tab_2_tv)).perform(click())
        onView(withId(R.id.tv_recycler_view)).check(matches(isDisplayed()))

        onView(withId(R.id.menu_sort_by_size)).check(matches(isDisplayed()))
        onView(withId(R.id.menu_sort_by_size)).perform(click())
        onView(withText(R.string.by_release)).check(matches(isDisplayed()))
        onView(withText(R.string.by_name)).check(matches(isDisplayed()))
        onView(withText(R.string.by_release)).perform(click())
    }
}