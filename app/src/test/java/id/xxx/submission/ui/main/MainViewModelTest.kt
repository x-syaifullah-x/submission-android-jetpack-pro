package id.xxx.submission.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import id.xxx.submission.data.Resource
import id.xxx.submission.data.TheMovieDBRepository
import id.xxx.submission.data.source.remote.RemoteDataSourceTest.Companion.errorMessage
import id.xxx.submission.data.source.remote.response.MovieResponse
import id.xxx.submission.data.source.remote.response.TvResponse
import id.xxx.submission.utils.DummyDataResponse
import id.xxx.submission.utils.LiveDataTestUtil.getValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel

    private val dummyDataMovie = DummyDataResponse.movieResponse()
    private val dummyDataTv = DummyDataResponse.tvResponse()

    @Mock
    lateinit var theMovieDBRepository: TheMovieDBRepository

    @Mock
    lateinit var observerResourceMovie: Observer<Resource<MovieResponse>>

    @Mock
    lateinit var observerResourceTv: Observer<Resource<TvResponse>>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(theMovieDBRepository)
    }

    @Test
    fun get_data_movie_resource_success() {
        `when`(theMovieDBRepository.getDataMovie())
            .thenReturn(MutableLiveData(Resource.Success(dummyDataMovie)))

        val resource = getValue(mainViewModel.getDataMovie)
        verify(theMovieDBRepository).getDataMovie()

        mainViewModel.getDataMovie.observeForever(observerResourceMovie)
        verify(observerResourceMovie).onChanged(Resource.Success(dummyDataMovie))

        assertTrue(resource is Resource.Success)
        when (resource) {
            is Resource.Success -> {
                assertEquals(dummyDataMovie, resource.data)
            }
        }
    }

    @Test
    fun get_data_movie_resource_error() {
        `when`(theMovieDBRepository.getDataMovie())
            .thenReturn(MutableLiveData(Resource.Error(errorMessage)))

        val resource = getValue(mainViewModel.getDataMovie)
        verify(theMovieDBRepository).getDataMovie()

        mainViewModel.getDataMovie.observeForever(observerResourceMovie)
        verify(observerResourceMovie).onChanged(Resource.Error(errorMessage))

        assertTrue(resource is Resource.Error)
        when (resource) {
            is Resource.Error -> {
                assertEquals(errorMessage, resource.errorMessage)
            }
        }
    }

    @Test
    fun get_data_movie_resource_empty() {
        `when`(theMovieDBRepository.getDataMovie())
            .thenReturn(MutableLiveData(Resource.Empty(null)))

        val resource = getValue(mainViewModel.getDataMovie)
        verify(theMovieDBRepository).getDataMovie()

        mainViewModel.getDataMovie.observeForever(observerResourceMovie)
        verify(observerResourceMovie).onChanged(Resource.Empty(null))

        assertTrue(resource is Resource.Empty)
        when (resource) {
            is Resource.Empty -> {
                assertNull(resource.data)
            }
        }
    }

    @Test
    fun get_data_tv_resource_success() {
        `when`(theMovieDBRepository.getDataTv())
            .thenReturn(MutableLiveData(Resource.Success(dummyDataTv)))

        val resource = getValue(mainViewModel.getDataTv)
        verify(theMovieDBRepository).getDataTv()

        mainViewModel.getDataTv.observeForever(observerResourceTv)
        verify(observerResourceTv).onChanged(Resource.Success(dummyDataTv))

        assertTrue(resource is Resource.Success)
        when (resource) {
            is Resource.Success -> {
                assertEquals(dummyDataTv, resource.data)
            }
        }
    }

    @Test
    fun get_data_tv_resource_error() {
        `when`(theMovieDBRepository.getDataTv())
            .thenReturn(MutableLiveData(Resource.Error(errorMessage)))

        val resource = getValue(mainViewModel.getDataTv)
        verify(theMovieDBRepository).getDataTv()

        mainViewModel.getDataTv.observeForever(observerResourceTv)
        verify(observerResourceTv).onChanged(Resource.Error(errorMessage))

        assertTrue(resource is Resource.Error)
        when (resource) {
            is Resource.Error -> {
                assertEquals(errorMessage, resource.errorMessage)
            }
        }
    }

    @Test
    fun get_data_tv_resource_empty() {
        `when`(theMovieDBRepository.getDataTv())
            .thenReturn(MutableLiveData(Resource.Empty(null)))

        val resource = getValue(mainViewModel.getDataTv)
        verify(theMovieDBRepository).getDataTv()

        mainViewModel.getDataTv.observeForever(observerResourceTv)
        verify(observerResourceTv).onChanged(Resource.Empty(null))

        assertTrue(resource is Resource.Empty)
        when (resource) {
            is Resource.Empty -> {
                assertNull(resource.data)
            }
        }
    }
}