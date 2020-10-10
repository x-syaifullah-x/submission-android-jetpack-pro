package id.xxx.submission.ui.movie

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import id.xxx.submission.R
import id.xxx.submission.SingleFragmentActivity
import id.xxx.submission.data.ResourcesData
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class MovieFragmentTest {
    private val ctx = ApplicationProvider.getApplicationContext<Context>()
    private val dataMovie = ResourcesData.getDataMovie(ctx)
    private val movieFragment = MovieFragment()

    @get:Rule
    val scenario = ActivityScenarioRule(SingleFragmentActivity::class.java)

    @Test
    fun loadMovie() {
        scenario.scenario.onActivity { it.setFragment(movieFragment) }
        onView(ViewMatchers.withId(R.id.recycler_view)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dataMovie.size - 1))

        val randomPosition = Random.nextInt(0, dataMovie.size - 1)
        onView(ViewMatchers.withId(R.id.recycler_view)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(randomPosition, ViewActions.click())
        )
    }
}