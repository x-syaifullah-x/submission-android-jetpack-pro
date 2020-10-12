package id.xxx.submission.ui.detail

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import id.xxx.submission.R
import id.xxx.submission.ui.detail.DetailActivity.Companion.DATA_EXTRA
import id.xxx.submission.utils.DummyData
import id.xxx.submission.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class DetailActivityTest {
    private lateinit var scenario: ActivityScenario<DetailActivity>
    private val dummyDataMovie = DummyData.movieDetailModel()
    private val dummyDataTv = DummyData.tvDetailModel()
    private val ctx = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun detailMovieLoaded() {
        scenario = ActivityScenario.launch(
            Intent(ctx, DetailActivity::class.java).apply {
                putExtra(DATA_EXTRA, arrayListOf(R.id.detail_movie, dummyDataMovie.id))
            }
        )
        onView(withId(R.id.overview_content)).check(matches(isDisplayed()))
    }

    @Test
    fun detailTvLoaded() {
        scenario = ActivityScenario.launch(
            Intent(ctx, DetailActivity::class.java).apply {
                putExtra(DATA_EXTRA, arrayListOf(R.id.detail_tv, dummyDataTv.id))
            }
        )
        onView(withId(R.id.overview_content)).check(matches(isDisplayed()))
    }

    @Test
    fun set_favorite() {
        scenario = ActivityScenario.launch(
            Intent(ctx, DetailActivity::class.java).apply {
                putExtra(DATA_EXTRA, arrayListOf(R.id.detail_movie, dummyDataMovie.id))
            }
        )
        onView(withId(R.id.fab)).check(matches(isDisplayed()))
        onView(withId(R.id.fab)).perform(click())
        onView(withText(R.string.ok)).check(matches(isDisplayed()))
    }
}