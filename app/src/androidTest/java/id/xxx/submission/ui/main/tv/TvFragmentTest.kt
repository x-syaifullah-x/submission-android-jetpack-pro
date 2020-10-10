package id.xxx.submission.ui.main.tv

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import id.xxx.submission.R
import id.xxx.submission.SingleNavigationActivity
import id.xxx.submission.data.source.remote.response.the_movie_db.TvResult
import id.xxx.submission.utils.EspressoIdlingResource
import kotlinx.android.synthetic.main.fragment_tv.*
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random.Default.nextInt

class TvFragmentTest {

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
    fun fragment_tv_loaded() {
        asr.scenario.onActivity {
            it.startDestination(R.navigation.nav_graph_home, R.id.fragment_tv)
        }

        onView(withId(R.id.tv_recycler_view)).check(matches(isDisplayed()))

        val data = mutableListOf<TvResult>()
        asr.scenario.onActivity {
            data.addAll((it.tv_recycler_view.adapter as AdapterTv).data)
        }

        assertTrue(data.size > 0)
        onView(withId(R.id.tv_recycler_view))
            .perform(scrollToPosition<ViewHolder>(data.size - 1))

        onView(withId(R.id.tv_recycler_view))
            .perform(actionOnItemAtPosition<ViewHolder>(nextInt(0, data.size - 1), click()))
    }
}