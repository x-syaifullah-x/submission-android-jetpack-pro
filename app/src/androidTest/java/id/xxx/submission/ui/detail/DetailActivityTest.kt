package id.xxx.submission.ui.detail

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import id.xxx.submission.R
import id.xxx.submission.ui.detail.DetailActivity.Companion.DATA_EXTRA
import id.xxx.submission.utils.DummyDataResponse
import id.xxx.submission.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class DetailActivityTest {
    private lateinit var scenario: ActivityScenario<DetailActivity>
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
        val data = DummyDataResponse.movieDetailResponse()
        scenario = ActivityScenario.launch(
            Intent(ctx, DetailActivity::class.java).apply {
                putExtra(DATA_EXTRA, arrayListOf(R.id.detail_movie, data.id))
            }
        )

        onView(withText(data.title)).check(matches(isDisplayed()))
        onView(withText(data.title)).check(matches(withId(R.id.title_content)))
        onView(withText(data.overview)).check(matches(isDisplayed()))
        onView(withText(data.overview)).check(matches(withId(R.id.overview_content)))
    }

    @Test
    fun detailTvLoaded() {
        val data = DummyDataResponse.tvDetailResponse()
        scenario = ActivityScenario.launch(
            Intent(ctx, DetailActivity::class.java).apply {
                putExtra(DATA_EXTRA, arrayListOf(R.id.detail_tv, data.id))
            }
        )

        onView(withText(data.original_name)).check(matches(isDisplayed()))
        onView(withText(data.original_name)).check(matches(withId(R.id.title_content)))
        onView(withText(data.overview)).check(matches(isDisplayed()))
        onView(withText(data.overview)).check(matches(withId(R.id.overview_content)))
    }
}