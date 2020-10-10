package id.xxx.submission.ui

import android.app.Activity
import android.content.Context
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import id.xxx.submission.R
import id.xxx.submission.data.ResourcesData
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class MainActivityTest {
    private val ctx = ApplicationProvider.getApplicationContext<Context>()
    private val dataMovie = ResourcesData.getDataMovie(ctx)
    private val dataTv = ResourcesData.getDataTv(ctx)

    @get:Rule
    val asr = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun navigationTest() {
        onView(withId(R.id.bottom_navigation_view)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tv)).check(ViewAssertions.matches(isDisplayed()))

        val currentDes =
            { a: Activity -> a.findNavController(R.id.nav_host_fragment).currentDestination }

        asr.scenario.onActivity {
            Assert.assertEquals(ctx.getString(R.string.movies), currentDes(it)?.label)
        }

        onView(withId(R.id.tv)).perform(click())
        asr.scenario.onActivity {
            Assert.assertEquals(ctx.getString(R.string.tv_show), currentDes(it)?.label)
        }

        onView(withId(R.id.movie)).perform(click())
        asr.scenario.onActivity {
            Assert.assertEquals(ctx.getString(R.string.movies), currentDes(it)?.label)
        }
    }

    @Test
    fun moviesTest() {
        onView(withId(R.id.movie)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.recycler_view)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<ViewHolder>(dataMovie.size - 1))

        val randomPosition = Random.nextInt(0, dataMovie.size - 1)
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(randomPosition, click())
        )
    }

    @Test
    fun tvShowTest() {
        onView(withId(R.id.tv)).perform(click())
        onView(withId(R.id.tv)).check(ViewAssertions.matches(isDisplayed()))

        onView(withId(R.id.recycler_view)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.scrollToPosition<ViewHolder>(dataTv.size - 1)
        )

        val randomPosition = Random.nextInt(0, dataTv.size - 1)
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(randomPosition, click())
        )
    }
}