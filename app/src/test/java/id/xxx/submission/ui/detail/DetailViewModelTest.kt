package id.xxx.submission.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import id.xxx.submission.R
import id.xxx.submission.data.Resource
import id.xxx.submission.data.TheMovieDBRepository
import id.xxx.submission.data.source.remote.response.MovieDetailResponse
import id.xxx.submission.data.source.remote.response.TvDetailResponse
import id.xxx.submission.utils.DummyDataResponse
import id.xxx.submission.utils.LiveDataTestUtil.getValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var detailViewModel: DetailViewModel

    @Mock
    lateinit var repository: TheMovieDBRepository

    @Mock
    lateinit var observerMovieDetail: Observer<Resource<MovieDetailResponse>>

    @Mock
    lateinit var observerTvDetail: Observer<Resource<TvDetailResponse>>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(repository)
    }

    @Test
    fun detail_data_movie_resource_success() {
        val dummyData = DummyDataResponse.movieDetailResponse()
        val dataDes = R.id.detail_movie
        val dataId = dummyData.id

        `when`(repository.getDataMovieById(dataId))
            .thenReturn(MutableLiveData(Resource.Success(dummyData)))

        detailViewModel.setDataExtra(dataDes, dataId)
        assertEquals(dataDes, detailViewModel.getDataExtra(DetailViewModel.DATA_DESTINATION))
        assertEquals(dataId, detailViewModel.getDataExtra(DetailViewModel.DATA_ID))
        verify(repository).getDataMovieById(dataId)
        assertNotNull(detailViewModel.dataMovie)

        detailViewModel.dataMovie?.observeForever(observerMovieDetail)
        verify(observerMovieDetail).onChanged(Resource.Success(dummyData))

        val resource = getValue(detailViewModel.dataMovie)
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
        val dummyData = DummyDataResponse.tvDetailResponse()
        val dataDes = R.id.detail_tv
        val dataId = dummyData.id

        `when`(repository.getDataTvById(dataId))
            .thenReturn(MutableLiveData(Resource.Success(dummyData)))

        detailViewModel.setDataExtra(dataDes, dataId)
        assertEquals(dataDes, detailViewModel.getDataExtra(DetailViewModel.DATA_DESTINATION))
        assertEquals(dataId, detailViewModel.getDataExtra(DetailViewModel.DATA_ID))
        verify(repository).getDataTvById(dataId)
        assertNotNull(detailViewModel.dataTv)

        detailViewModel.dataTv?.observeForever(observerTvDetail)
        verify(observerTvDetail).onChanged(Resource.Success(dummyData))

        val resource = getValue(detailViewModel.dataTv)
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