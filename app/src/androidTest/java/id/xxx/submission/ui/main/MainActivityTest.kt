package id.xxx.submission.ui.main

import android.app.Activity
import android.content.Context
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import id.xxx.submission.R
import id.xxx.submission.R.id.*
import id.xxx.submission.R.string.favorite
import id.xxx.submission.data.model.MovieResultModel
import id.xxx.submission.data.model.TvResultModel
import id.xxx.submission.ui.main.movie.AdapterMovie
import id.xxx.submission.ui.main.tv.AdapterTv
import id.xxx.submission.utils.EspressoIdlingResource.espressoTestIdlingResource
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_tv.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random.Default.nextInt

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

        onView(withId(bottom_navigation_view)).check(matches(isDisplayed()))
        onView(withId(fragment_movie)).check(matches(isDisplayed()))
        onView(withId(movie_recycler_view)).check(matches(isDisplayed()))

        onView(withId(menu_sort_by_size)).check(matches(isDisplayed()))
        onView(withId(menu_sort_by_size)).perform(click())
        onView(withText(R.string.by_name)).check(matches(isDisplayed()))
        onView(withText(R.string.by_release)).check(matches(isDisplayed()))
        onView(withText(R.string.by_release)).perform(click())

        val data = mutableListOf<MovieResultModel>()
        asr.scenario.onActivity { activity ->
            (activity.movie_recycler_view.adapter as AdapterMovie).currentList?.map { data.add(it) }
        }

        onView(withId(movie_recycler_view))
            .perform(scrollToPosition<ViewHolder>(data.size - 1))

        onView(withId(movie_recycler_view))
            .perform(
                actionOnItemAtPosition<ViewHolder>(nextInt(0, data.size - 1), click())
            )
        onView(isRoot()).perform(pressBack())
    }

    @Test
    fun bottom_navigation_move_to_tv() {
        onView(withId(fragment_tv)).perform(click())
        asr.scenario.onActivity {
            assertEquals(ctx.getString(R.string.tv_show), currentDes(it)?.label)
        }
        onView(withId(fragment_tv)).check(matches(isDisplayed()))
        onView(withId(tv_recycler_view)).check(matches(isDisplayed()))

        onView(withId(menu_sort_by_size)).check(matches(isDisplayed()))
        onView(withId(menu_sort_by_size)).perform(click())
        onView(withText(R.string.by_release)).check(matches(isDisplayed()))
        onView(withText(R.string.by_name)).check(matches(isDisplayed()))
        onView(withText(R.string.by_release)).perform(click())

        val data = mutableListOf<TvResultModel>()
        asr.scenario.onActivity { activity ->
            (activity.tv_recycler_view.adapter as AdapterTv).currentList?.map { data.add(it) }
        }

        onView(withId(tv_recycler_view))
            .perform(scrollToPosition<ViewHolder>(data.size - 1))

        onView(withId(tv_recycler_view)).perform(
            actionOnItemAtPosition<ViewHolder>(nextInt(0, data.size - 1), click())
        )
        onView(isRoot()).perform(pressBack())
    }

    @Test
    fun bottom_navigation_move_to_favorite_tab_movie() {
        onView(withId(fragment_favorite)).perform(click())
        asr.scenario.onActivity { assertEquals(ctx.getString(favorite), currentDes(it)?.label) }
        onView(withId(fragment_favorite)).check(matches(isDisplayed()))
        onView(withId(movie_recycler_view)).check(matches(isDisplayed()))

        val data = mutableListOf<MovieResultModel>()
        asr.scenario.onActivity { activity ->
            (activity.movie_recycler_view.adapter as AdapterMovie).currentList?.map { data.add(it) }
        }

        if (data.size > 0) {
            onView(withId(menu_sort_by_size)).check(matches(isDisplayed()))
            onView(withId(menu_sort_by_size)).perform(click())
            onView(withText(R.string.by_release)).check(matches(isDisplayed()))
            onView(withText(R.string.by_name)).check(matches(isDisplayed()))
            onView(withText(R.string.by_release)).perform(click())

            onView(withId(movie_recycler_view))
                .perform(scrollToPosition<ViewHolder>(data.size - 1))
            onView(withId(movie_recycler_view))
                .perform(actionOnItemAtPosition<ViewHolder>(0, click()))
            onView(isRoot()).perform(pressBack())
        }
    }

    @Test
    fun bottom_navigation_move_to_favorite_tab_tv() {
        onView(withId(fragment_favorite)).perform(click())
        asr.scenario.onActivity { assertEquals(ctx.getString(favorite), currentDes(it)?.label) }

        onView(withId(fragment_favorite)).check(matches(isDisplayed()))
        onView(withText(R.string.tab_2_tv)).perform(click())
        onView(withId(tv_recycler_view)).check(matches(isDisplayed()))

        val data = mutableListOf<TvResultModel>()
        asr.scenario.onActivity { activity ->
            (activity.tv_recycler_view.adapter as AdapterTv).currentList?.map { data.add(it) }
        }

        if (data.size > 0) {
            onView(withId(menu_sort_by_size)).check(matches(isDisplayed()))
            onView(withId(menu_sort_by_size)).perform(click())
            onView(withText(R.string.by_release)).check(matches(isDisplayed()))
            onView(withText(R.string.by_name)).check(matches(isDisplayed()))
            onView(withText(R.string.by_release)).perform(click())

            onView(withId(tv_recycler_view))
                .perform(scrollToPosition<ViewHolder>(data.size - 1))
            onView(withId(tv_recycler_view))
                .perform(actionOnItemAtPosition<ViewHolder>(0, click()))
            onView(isRoot()).perform(pressBack())
        }
    }
}