package id.xxx.submission.ui.detail

import android.content.Context
import android.content.Intent
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import id.xxx.submission.R
import id.xxx.submission.data.ResourcesData
import id.xxx.submission.entity.DataEntity
import org.junit.Assert
import org.junit.Test

class DetailActivityTest {
    private lateinit var scenario: ActivityScenario<DetailActivity>

    private val ctx = ApplicationProvider.getApplicationContext<Context>()
    private val dataMovie = ResourcesData.getDataMovie(ctx)
    private val dataTv = ResourcesData.getDataTv(ctx)

    @Test
    fun loadDetailActivityWithDataMovie() {
        dataMovie.forEach { data ->
            scenario = ActivityScenario.launch(intentData(data))
            scenario.onActivity { verifyAllAssertActivityDetail(it, data) }
        }
    }

    @Test
    fun loadDetailActivityWithDataTv() {
        dataTv.forEach { data ->
            scenario = ActivityScenario.launch(intentData(data))
            scenario.onActivity { verifyAllAssertActivityDetail(it, data) }
        }
    }

    private fun intentData(data: DataEntity): Intent {
        return Intent(ctx, DetailActivity::class.java).putExtra(DetailActivity.DATA_EXTRA, data)
    }

    private fun verifyAllAssertActivityDetail(activity: DetailActivity, data: DataEntity) {
        val poster = activity.findViewById<AppCompatImageView>(R.id.poster_detail)
        Assert.assertNotNull(poster.background)

        val titleContent = activity.findViewById<AppCompatTextView>(R.id.title_content)
        Assert.assertEquals(data.title, titleContent.text)

        val releaseContent = activity.findViewById<AppCompatTextView>(R.id.release_content)
        Assert.assertEquals(data.releaseData, releaseContent.text)

        val userScoreContent = activity.findViewById<RatingBar>(R.id.user_score_content)
        Assert.assertEquals(data.voteAverage?.toFloat(), userScoreContent.rating)

        val genreContent = activity.findViewById<AppCompatTextView>(R.id.genre_content)
        Assert.assertEquals(data.genre, genreContent.text)

        val overViewContent = activity.findViewById<AppCompatTextView>(R.id.overview_content)
        Assert.assertEquals(data.overview, overViewContent.text)
    }
}