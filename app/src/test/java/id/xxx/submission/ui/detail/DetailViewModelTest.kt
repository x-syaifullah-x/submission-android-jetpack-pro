package id.xxx.submission.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import id.xxx.submission.R
import id.xxx.submission.data.Resource
import id.xxx.submission.data.model.MovieDetailModel
import id.xxx.submission.data.model.TvDetailModel
import id.xxx.submission.data.repository.MovieRepository
import id.xxx.submission.data.repository.TvRepository
import id.xxx.submission.ui.detail.DetailActivity.Companion.DATA_DESTINATION
import id.xxx.submission.ui.detail.DetailActivity.Companion.DATA_ID
import id.xxx.submission.utils.DummyData
import id.xxx.submission.utils.LiveDataTestUtil.getValue
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var detailViewModel: DetailViewModel

    @Mock
    lateinit var tvRepo: TvRepository

    @Mock
    lateinit var movieRepo: MovieRepository

    @Mock
    lateinit var observerMovieDetail: Observer<Resource<MovieDetailModel>>

    @Mock
    lateinit var observerTvDetail: Observer<Resource<TvDetailModel>>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(movieRepo, tvRepo)
    }

    @Test
    fun detail_data_movie_resource_success() {
        val dummyData = DummyData.movieDetailModel()
        val dataDes = R.id.detail_movie
        val dataId = dummyData.id

        `when`(movieRepo.getDetail(dataId))
            .thenReturn(MutableLiveData(Resource.Success(dummyData)))

        detailViewModel.init(dataDes, dataId)
        assertEquals(dataDes, detailViewModel.getExtra(DATA_DESTINATION))
        assertEquals(dataId, detailViewModel.getExtra(DATA_ID))
        verify(movieRepo).getDetail(dataId)
        assertNotNull(detailViewModel.movie)

        detailViewModel.movie.observeForever(observerMovieDetail)
        verify(observerMovieDetail).onChanged(Resource.Success(dummyData))

        val resource = getValue(detailViewModel.movie)
        assertNotNull(resource)
        assertTrue(resource is Resource.Success)
        when (resource) {
            is Resource.Success -> {
                assertEquals(dummyData, resource.data)
                assertEquals(dataId, resource.data.id)
            }
        }
    }

    @Test
    fun detail_data_tv_resource_success() {
        val dummyData = DummyData.tvDetailModel()
        val dataDes = R.id.detail_tv
        val dataId = dummyData.id

        `when`(tvRepo.getDetail(dataId))
            .thenReturn(MutableLiveData(Resource.Success(dummyData)))

        detailViewModel.init(dataDes, dataId)
        assertEquals(dataDes, detailViewModel.getExtra(DATA_DESTINATION))
        assertEquals(dataId, detailViewModel.getExtra(DATA_ID))
        verify(tvRepo).getDetail(dataId)
        assertNotNull(detailViewModel.tv)

        detailViewModel.tv.observeForever(observerTvDetail)
        verify(observerTvDetail).onChanged(Resource.Success(dummyData))
        val resource = getValue(detailViewModel.tv)
        assertNotNull(resource)
        assertTrue(resource is Resource.Success)
        when (resource) {
            is Resource.Success -> {
                assertEquals(dummyData, resource.data)
                assertEquals(dataId, resource.data.id)
            }
        }
    }
}