package id.xxx.submission.ui.main.tv

import androidx.recyclerview.widget.RecyclerView.ViewHolder
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
import id.xxx.submission.R.id.fragment_tv
import id.xxx.submission.R.navigation.nav_graph_home
import id.xxx.submission.SingleNavigationActivity
import id.xxx.submission.data.model.TvResultModel
import id.xxx.submission.utils.EspressoIdlingResource
import kotlinx.android.synthetic.main.fragment_tv.*
import org.junit.After
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
    fun fragment_loaded() {
        val scenario = asr.scenario
        scenario.onActivity { it.startDestination(nav_graph_home, fragment_tv) }

        onView(withId(R.id.tv_recycler_view)).check(matches(isDisplayed()))
        val data = mutableListOf<TvResultModel>()
        scenario.onActivity { activity ->
            (activity.tv_recycler_view.adapter as AdapterTv).currentList?.map { data.add(it) }
        }

        onView(withId(R.id.tv_recycler_view))
            .perform(scrollToPosition<ViewHolder>(data.size - 1))

        if (data.size > 0) {
            onView(withId(R.id.tv_recycler_view))
                .perform(actionOnItemAtPosition<ViewHolder>(nextInt(0, data.size - 1), click()))
            onView(isRoot()).perform(pressBack())
        }
    }
}