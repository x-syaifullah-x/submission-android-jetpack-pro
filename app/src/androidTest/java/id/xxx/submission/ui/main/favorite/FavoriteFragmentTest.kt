package id.xxx.submission.ui.main.favorite

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import id.xxx.submission.R
import id.xxx.submission.R.id.fragment_favorite
import id.xxx.submission.R.navigation.nav_graph_home
import id.xxx.submission.SingleNavigationActivity
import id.xxx.submission.data.model.MovieResultModel
import id.xxx.submission.ui.main.movie.AdapterMovie
import id.xxx.submission.utils.EspressoIdlingResource
import kotlinx.android.synthetic.main.fragment_movie.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoriteFragmentTest {
    @get:Rule
    val asr = ActivityScenarioRule(SingleNavigationActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun fragment_loaded() {
        val scenario = asr.scenario
        scenario.onActivity { it.startDestination(nav_graph_home, fragment_favorite) }

        onView(ViewMatchers.withId(R.id.movie_recycler_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        val data = mutableListOf<MovieResultModel>()
        scenario.onActivity { activity ->
            (activity.movie_recycler_view.adapter as AdapterMovie).currentList?.map { data.add(it) }
        }

        onView(ViewMatchers.withId(R.id.movie_recycler_view))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(data.size - 1))

        if (data.size > 0)
            onView(ViewMatchers.withId(R.id.movie_recycler_view)).perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click())
            )
    }
}