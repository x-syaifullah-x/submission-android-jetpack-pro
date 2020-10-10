package id.xxx.submission.ui.tv

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import id.xxx.submission.R
import id.xxx.submission.SingleFragmentActivity
import id.xxx.submission.data.ResourcesData
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class TvFragmentTest {
    private val ctx = ApplicationProvider.getApplicationContext<Context>()
    private val dataTv = ResourcesData.getDataTv(ctx)

    @get:Rule
    val scenario = ActivityScenarioRule(SingleFragmentActivity::class.java)
    private val tvFragment = TvFragment()

    @Test
    fun loadTv() {
        scenario.scenario.onActivity { it.setFragment(tvFragment) }
        onView(withId(R.id.recycler_view)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.recycler_view)).perform(scrollToPosition<RecyclerView.ViewHolder>(dataTv.size - 1))
        val randomPosition = Random.nextInt(0, dataTv.size - 1)
        onView(withId(R.id.recycler_view)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(randomPosition, ViewActions.click())
        )
    }
}