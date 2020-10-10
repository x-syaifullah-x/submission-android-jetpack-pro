package id.xxx.submission.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import id.xxx.submission.data.source.remote.RemoteDataSource
import id.xxx.submission.data.source.remote.RemoteDataSourceTest
import id.xxx.submission.data.source.remote.network.ApiResponse
import id.xxx.submission.data.source.remote.response.MovieDetailResponse
import id.xxx.submission.data.source.remote.response.MovieResponse
import id.xxx.submission.data.source.remote.response.TvDetailResponse
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
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TheMovieDBRepositoryTest {

    private lateinit var theMovieDBRepository: TheMovieDBRepositoryFake

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var observerResourceMovieResponse: Observer<Resource<MovieResponse>>

    @Mock
    lateinit var observerResourceTvResponse: Observer<Resource<TvResponse>>

    @Mock
    lateinit var observerResourceDetailMovieResponse: Observer<Resource<MovieDetailResponse>>

    @Mock
    lateinit var observerResourceDetailTvResponse: Observer<Resource<TvDetailResponse>>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        theMovieDBRepository = TheMovieDBRepositoryFake(remoteDataSource)
    }

    @Test
    fun get_data_movie_resource_success() {
        val dummyData = DummyDataResponse.movieResponse()

        `when`(remoteDataSource.getDataMovie())
            .thenReturn(MutableLiveData(ApiResponse.Success(dummyData)))

        val resource = getValue(theMovieDBRepository.getDataMovie())

        verify(remoteDataSource).getDataMovie()

        theMovieDBRepository.getDataMovie().observeForever(observerResourceMovieResponse)
        verify(observerResourceMovieResponse).onChanged(Resource.Success(dummyData))

        assertNotNull(resource)
        assertTrue(resource is Resource.Success)

        when (resource) {
            is Resource.Success -> {
                assertNotNull(resource.data)
                assertEquals(dummyData, resource.data)
                assertTrue(resource.data.results.isNotEmpty())
            }
        }
    }

    @Test
    fun get_data_movie_resource_empty() {
        `when`(remoteDataSource.getDataMovie())
            .thenReturn(MutableLiveData(ApiResponse.Empty(null)))

        val resource = getValue(theMovieDBRepository.getDataMovie())

        verify(remoteDataSource).getDataMovie()

        theMovieDBRepository.getDataMovie().observeForever(observerResourceMovieResponse)
        verify(observerResourceMovieResponse).onChanged(Resource.Empty(null))

        assertNotNull(resource)
        assertTrue(resource is Resource.Empty)

        when (resource) {
            is Resource.Empty -> {
                assertNull(resource.data)
            }
        }
    }

    @Test
    fun get_data_movie_resource_error() {
        `when`(remoteDataSource.getDataMovie())
            .thenReturn(MutableLiveData(ApiResponse.Error(RemoteDataSourceTest.errorMessage)))

        val resource = getValue(theMovieDBRepository.getDataMovie())

        verify(remoteDataSource).getDataMovie()

        theMovieDBRepository.getDataMovie().observeForever(observerResourceMovieResponse)
        verify(observerResourceMovieResponse).onChanged(Resource.Error(RemoteDataSourceTest.errorMessage))

        assertNotNull(resource)
        assertTrue(resource is Resource.Error)

        when (resource) {
            is Resource.Error -> {
                assertNotNull(resource.errorMessage)
                assertEquals(RemoteDataSourceTest.errorMessage, resource.errorMessage)
            }
        }
    }

    @Test
    fun get_data_tv_resource_success() {
        val dummyData = DummyDataResponse.tvResponse()

        `when`(remoteDataSource.getDataTv())
            .thenReturn(MutableLiveData(ApiResponse.Success(dummyData)))

        val resource = getValue(theMovieDBRepository.getDataTv())

        verify(remoteDataSource).getDataTv()

        theMovieDBRepository.getDataTv().observeForever(observerResourceTvResponse)
        verify(observerResourceTvResponse).onChanged(Resource.Success(dummyData))

        assertNotNull(resource)
        assertTrue(resource is Resource.Success)

        when (resource) {
            is Resource.Success -> {
                assertNotNull(resource.data)
                assertEquals(dummyData, resource.data)
                assertTrue(resource.data.results.isNotEmpty())
            }
        }
    }

    @Test
    fun get_data_tv_resource_empty() {
        `when`(remoteDataSource.getDataTv())
            .thenReturn(MutableLiveData(ApiResponse.Empty(null)))

        val resource = getValue(theMovieDBRepository.getDataTv())

        verify(remoteDataSource).getDataTv()

        theMovieDBRepository.getDataTv().observeForever(observerResourceTvResponse)
        verify(observerResourceTvResponse).onChanged(Resource.Empty(null))

        assertNotNull(resource)
        assertTrue(resource is Resource.Empty)

        when (resource) {
            is Resource.Empty -> {
                assertNull(resource.data)
            }
        }
    }

    @Test
    fun get_data_tv_resource_error() {
        `when`(remoteDataSource.getDataTv())
            .thenReturn(MutableLiveData(ApiResponse.Error(RemoteDataSourceTest.errorMessage)))

        val resource = getValue(theMovieDBRepository.getDataTv())

        verify(remoteDataSource).getDataTv()

        theMovieDBRepository.getDataTv().observeForever(observerResourceTvResponse)
        verify(observerResourceTvResponse).onChanged(Resource.Error(RemoteDataSourceTest.errorMessage))

        assertNotNull(resource)
        assertTrue(resource is Resource.Error)

        when (resource) {
            is Resource.Error -> {
                assertNotNull(resource.errorMessage)
                assertEquals(RemoteDataSourceTest.errorMessage, resource.errorMessage)
            }
        }
    }

    @Test
    fun get_detail_data_movie_resource_success() {
        val dummyData = DummyDataResponse.movieDetailResponse()
        val idData = dummyData.id

        `when`(remoteDataSource.getDataMovieById(idData))
            .thenReturn(MutableLiveData(ApiResponse.Success(dummyData)))

        val resource = getValue(theMovieDBRepository.getDataMovieById(idData))

        verify(remoteDataSource).getDataMovieById(idData)

        theMovieDBRepository.getDataMovieById(idData)
            .observeForever(observerResourceDetailMovieResponse)
        verify(observerResourceDetailMovieResponse).onChanged(Resource.Success(dummyData))

        assertNotNull(resource)
        assertTrue(resource is Resource.Success)

        when (resource) {
            is Resource.Success -> {
                assertNotNull(resource.data)
                assertEquals(dummyData, resource.data)
                assertTrue(resource.data.id == resource.data.id)
            }
        }
    }

    @Test
    fun get_detail_data_movie_resource_error() {
        val idData = DummyDataResponse.movieDetailResponse().id
        `when`(remoteDataSource.getDataMovieById(idData))
            .thenReturn(MutableLiveData(ApiResponse.Error(RemoteDataSourceTest.errorMessage)))

        val resource = getValue(theMovieDBRepository.getDataMovieById(idData))

        verify(remoteDataSource).getDataMovieById(idData)

        theMovieDBRepository.getDataMovieById(idData)
            .observeForever(observerResourceDetailMovieResponse)
        verify(observerResourceDetailMovieResponse).onChanged(Resource.Error(RemoteDataSourceTest.errorMessage))

        assertNotNull(resource)
        assertTrue(resource is Resource.Error)
        when (resource) {
            is Resource.Error -> {
                assertNotNull(resource.errorMessage)
                assertEquals(RemoteDataSourceTest.errorMessage, resource.errorMessage)
            }
        }
    }

    @Test
    fun get_detail_data_movie_resource_empty() {
        val idData = -500
        `when`(remoteDataSource.getDataMovieById(idData))
            .thenReturn(MutableLiveData(ApiResponse.Empty(null)))

        val resource = getValue(theMovieDBRepository.getDataMovieById(idData))

        verify(remoteDataSource).getDataMovieById(idData)

        theMovieDBRepository.getDataMovieById(idData)
            .observeForever(observerResourceDetailMovieResponse)
        verify(observerResourceDetailMovieResponse).onChanged(Resource.Empty(null))

        assertNotNull(resource)
        assertTrue(resource is Resource.Empty)
        when (resource) {
            is Resource.Empty -> {
                assertNull(resource.data)
            }
        }
    }

    @Test
    fun get_detail_data_tv_resource_success() {
        val dummyData = DummyDataResponse.tvDetailResponse()
        val idData = dummyData.id

        `when`(remoteDataSource.getDataTvById(idData))
            .thenReturn(MutableLiveData(ApiResponse.Success(dummyData)))

        val resource = getValue(theMovieDBRepository.getDataTvById(idData))

        verify(remoteDataSource).getDataTvById(idData)

        theMovieDBRepository.getDataTvById(idData)
            .observeForever(observerResourceDetailTvResponse)
        verify(observerResourceDetailTvResponse).onChanged(Resource.Success(dummyData))

        assertNotNull(resource)
        assertTrue(resource is Resource.Success)

        when (resource) {
            is Resource.Success -> {
                assertNotNull(resource.data)
                assertEquals(dummyData, resource.data)
                assertTrue(resource.data.id == resource.data.id)
            }
        }
    }

    @Test
    fun get_detail_data_tv_resource_error() {
        val idData = DummyDataResponse.tvDetailResponse().id
        `when`(remoteDataSource.getDataTvById(idData))
            .thenReturn(MutableLiveData(ApiResponse.Error(RemoteDataSourceTest.errorMessage)))

        val resource = getValue(theMovieDBRepository.getDataTvById(idData))

        verify(remoteDataSource).getDataTvById(idData)

        theMovieDBRepository.getDataTvById(idData).observeForever(observerResourceDetailTvResponse)
        verify(observerResourceDetailTvResponse).onChanged(Resource.Error(RemoteDataSourceTest.errorMessage))

        assertNotNull(resource)
        assertTrue(resource is Resource.Error)

        when (resource) {
            is Resource.Error -> {
                assertNotNull(resource.errorMessage)
                assertEquals(RemoteDataSourceTest.errorMessage, resource.errorMessage)
            }
        }
    }

    @Test
    fun get_detail_data_tv_resource_empty() {
        val idData = -500
        `when`(remoteDataSource.getDataTvById(idData))
            .thenReturn(MutableLiveData(ApiResponse.Empty(null)))

        val resource = getValue(theMovieDBRepository.getDataTvById(idData))

        verify(remoteDataSource).getDataTvById(idData)

        theMovieDBRepository.getDataTvById(idData).observeForever(observerResourceDetailTvResponse)
        verify(observerResourceDetailTvResponse).onChanged(Resource.Empty(null))

        assertNotNull(resource)
        assertTrue(resource is Resource.Empty)

        when (resource) {
            is Resource.Empty -> {
                assertNull(resource.data)
            }
        }
    }
}