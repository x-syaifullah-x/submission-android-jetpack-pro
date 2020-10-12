package id.xxx.submission.ui.main.favorite

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import id.xxx.submission.R.id.fragment_favorite_tv
import id.xxx.submission.R.id.tv_recycler_view
import id.xxx.submission.R.navigation.nav_graph_home
import id.xxx.submission.SingleNavigationActivity
import id.xxx.submission.data.model.TvResultModel
import id.xxx.submission.ui.main.tv.AdapterTv
import id.xxx.submission.utils.EspressoIdlingResource
import kotlinx.android.synthetic.main.fragment_tv.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoriteTvFragmentTest {
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
        scenario.onActivity {
            it.startDestination(nav_graph_home, fragment_favorite_tv)
        }

        onView(withId(tv_recycler_view))
            .check(matches(ViewMatchers.isDisplayed()))

        val data = mutableListOf<TvResultModel>()
        scenario.onActivity { activity ->
            (activity.tv_recycler_view.adapter as AdapterTv).currentList?.map { data.add(it) }
        }
        onView(withId(tv_recycler_view))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(data.size - 1))

        if (data.size > 0) {
            onView(withId(tv_recycler_view)).perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
            )
        } else {
            throw Exception("data favorite masih kosong")
        }
    }
}