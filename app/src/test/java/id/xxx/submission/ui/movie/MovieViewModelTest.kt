package id.xxx.submission.ui.movie

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class MovieViewModelTest {
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val movieViewModel: MovieViewModel = MovieViewModel(context as Application)

    @Test
    fun getDataMovie() {
        val data = movieViewModel.getDataMovie
        Assert.assertNotNull(data)
        Assert.assertNotEquals(0, data.size)
        Assert.assertEquals(10, data.size)

        data.forEach {
            Assert.assertNotEquals("", it.title)
            Assert.assertNotEquals("", it.releaseData)
            Assert.assertNotEquals("", it.originalLanguage)
            Assert.assertNotEquals("", it.originalCountry)
            Assert.assertNotEquals("", it.voteAverage)
            Assert.assertNotEquals("", it.overview)
            Assert.assertNotEquals("", it.genre)
            Assert.assertNotNull(it.posterName)
        }
    }
}