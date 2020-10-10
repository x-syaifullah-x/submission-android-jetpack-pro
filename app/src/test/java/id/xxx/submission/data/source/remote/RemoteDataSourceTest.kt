package id.xxx.submission.data.source.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import id.xxx.submission.data.source.remote.network.ApiResponse
import id.xxx.submission.data.source.remote.network.ApiServiceTheMovieDB
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
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNCHECKED_CAST")
@RunWith(MockitoJUnitRunner::class)
class RemoteDataSourceTest {

    companion object {
        const val errorMessage = "error message"
    }

    private lateinit var remoteDataSource: RemoteDataSourceFake

    @Mock
    private lateinit var apiServiceTheMovieDB: ApiServiceTheMovieDB

    @Mock
    private lateinit var callMovieResponse: Call<MovieResponse>

    @Mock
    private lateinit var callTvResponse: Call<TvResponse>

    @Mock
    private lateinit var callMovieDetailResponse: Call<MovieDetailResponse>

    @Mock
    private lateinit var callTvDetailResponse: Call<TvDetailResponse>

    @Mock
    private lateinit var observerApiResponseMovie: Observer<ApiResponse<MovieResponse>>

    @Mock
    private lateinit var observerApiResponseTv: Observer<ApiResponse<TvResponse>>

    @Mock
    private lateinit var observerApiResponseMovieDetail: Observer<ApiResponse<MovieDetailResponse>>

    @Mock
    private lateinit var observerApiResponseTvDetail: Observer<ApiResponse<TvDetailResponse>>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        remoteDataSource = RemoteDataSourceFake(apiServiceTheMovieDB)
    }

    @Test
    fun get_data_movie_response_success() {
        val dataDummy = DummyDataResponse.movieResponse()
        `when`(apiServiceTheMovieDB.getDataMovie()).thenReturn(callMovieResponse)

        doAnswer {
            (it.arguments[0] as Callback<MovieResponse>)
                .onResponse(callMovieResponse, Response.success(dataDummy))
        }.`when`(callMovieResponse)?.enqueue(any())

        val apiResponse = getValue(remoteDataSource.getDataMovie())

        verify(apiServiceTheMovieDB).getDataMovie()
        verify(apiServiceTheMovieDB.getDataMovie())?.enqueue(any())

        remoteDataSource.getDataMovie().observeForever(observerApiResponseMovie)
        verify(observerApiResponseMovie).onChanged(ApiResponse.Success(dataDummy))

        assertNotNull(apiResponse)
        assertTrue(apiResponse is ApiResponse.Success)
        when (apiResponse) {
            is ApiResponse.Success -> {
                assertEquals(dataDummy, apiResponse.data)
                assertEquals(dataDummy.results, apiResponse.data.results)
                assertEquals(dataDummy.results.size, apiResponse.data.results.size)
            }
        }
    }

    @Test
    fun get_data_movie_response_error() {
        `when`(apiServiceTheMovieDB.getDataMovie()).thenReturn(callMovieResponse)

        doAnswer {
            (it.arguments[0] as Callback<MovieResponse>)
                .onFailure(callMovieResponse, Throwable(errorMessage))
        }.`when`(callMovieResponse)?.enqueue(any())

        val apiResponse = getValue(remoteDataSource.getDataMovie())

        verify(apiServiceTheMovieDB).getDataMovie()
        verify(apiServiceTheMovieDB.getDataMovie())?.enqueue(any())

        remoteDataSource.getDataMovie().observeForever(observerApiResponseMovie)
        verify(observerApiResponseMovie).onChanged(ApiResponse.Error(errorMessage))

        assertNotNull(apiResponse)
        assertTrue(apiResponse is ApiResponse.Error)
        when (apiResponse) {
            is ApiResponse.Error -> {
                assertEquals(errorMessage, apiResponse.errorMessage)
            }
        }
    }

    @Test
    fun get_data_movie_response_empty() {
        `when`(apiServiceTheMovieDB.getDataMovie()).thenReturn(callMovieResponse)

        doAnswer {
            (it.arguments[0] as Callback<MovieResponse>)
                .onResponse(callMovieResponse, Response.success(null))
        }.`when`(callMovieResponse)?.enqueue(any())

        val apiResponse = getValue(remoteDataSource.getDataMovie())

        verify(apiServiceTheMovieDB).getDataMovie()
        verify(apiServiceTheMovieDB.getDataMovie())?.enqueue(any())

        remoteDataSource.getDataMovie().observeForever(observerApiResponseMovie)
        verify(observerApiResponseMovie).onChanged(ApiResponse.Empty(null))

        assertNotNull(apiResponse)
        assertTrue(apiResponse is ApiResponse.Empty)
        when (apiResponse) {
            is ApiResponse.Empty -> {
                assertNull(apiResponse.data)
            }
        }
    }

    @Test
    fun get_data_tv_response_success() {
        val dataDummy = DummyDataResponse.tvResponse()
        `when`(apiServiceTheMovieDB.getDataTv()).thenReturn(callTvResponse)

        doAnswer {
            (it.arguments[0] as Callback<TvResponse>)
                .onResponse(callTvResponse, Response.success(dataDummy))
        }.`when`(callTvResponse)?.enqueue(any())

        val apiResponse = getValue(remoteDataSource.getDataTv())

        verify(apiServiceTheMovieDB).getDataTv()
        verify(apiServiceTheMovieDB.getDataTv())?.enqueue(any())

        remoteDataSource.getDataTv().observeForever(observerApiResponseTv)
        verify(observerApiResponseTv).onChanged(ApiResponse.Success(dataDummy))

        assertNotNull(apiResponse)
        assertTrue(apiResponse is ApiResponse.Success)
        when (apiResponse) {
            is ApiResponse.Success -> {
                assertEquals(dataDummy, apiResponse.data)
                assertEquals(dataDummy.results, apiResponse.data.results)
                assertEquals(dataDummy.results.size, apiResponse.data.results.size)
            }
        }
    }

    @Test
    fun get_data_tv_response_error() {
        `when`(apiServiceTheMovieDB.getDataTv()).thenReturn(callTvResponse)

        doAnswer {
            (it.arguments[0] as Callback<TvResponse>)
                .onFailure(callTvResponse, Throwable(errorMessage))
        }.`when`(callTvResponse)?.enqueue(any())

        val apiResponse = getValue(remoteDataSource.getDataTv())

        verify(apiServiceTheMovieDB).getDataTv()
        verify(apiServiceTheMovieDB.getDataTv())?.enqueue(any())

        remoteDataSource.getDataTv().observeForever(observerApiResponseTv)
        verify(observerApiResponseTv).onChanged(ApiResponse.Error(errorMessage))

        assertNotNull(apiResponse)
        assertTrue(apiResponse is ApiResponse.Error)
        when (apiResponse) {
            is ApiResponse.Error -> {
                assertEquals(errorMessage, apiResponse.errorMessage)
            }
        }
    }

    @Test
    fun get_data_tv_response_empty() {
        `when`(apiServiceTheMovieDB.getDataTv()).thenReturn(callTvResponse)

        doAnswer {
            (it.arguments[0] as Callback<TvResponse>)
                .onResponse(callTvResponse, Response.success(null))
        }.`when`(callTvResponse)?.enqueue(any())

        val apiResponse = getValue(remoteDataSource.getDataTv())

        verify(apiServiceTheMovieDB).getDataTv()
        verify(apiServiceTheMovieDB.getDataTv())?.enqueue(any())

        remoteDataSource.getDataTv().observeForever(observerApiResponseTv)
        verify(observerApiResponseTv).onChanged(ApiResponse.Empty(null))

        assertNotNull(apiResponse)
        assertTrue(apiResponse is ApiResponse.Empty)
        when (apiResponse) {
            is ApiResponse.Empty -> {
                assertNull(apiResponse.data)
            }
        }
    }

    @Test
    fun get_detail_movie_response_success() {
        val dataDummy = DummyDataResponse.movieDetailResponse()
        val idData = dataDummy.id
        `when`(apiServiceTheMovieDB.getDataMovieById(idData)).thenReturn(callMovieDetailResponse)

        doAnswer {
            (it.arguments[0] as Callback<MovieDetailResponse>)
                .onResponse(callMovieDetailResponse, Response.success(dataDummy))
        }.`when`(callMovieDetailResponse)?.enqueue(any())

        val apiResponse = getValue(remoteDataSource.getDataMovieById(idData))

        verify(apiServiceTheMovieDB).getDataMovieById(idData)
        verify(apiServiceTheMovieDB.getDataMovieById(idData))?.enqueue(any())

        remoteDataSource.getDataMovieById(idData).observeForever(observerApiResponseMovieDetail)
        verify(observerApiResponseMovieDetail).onChanged(ApiResponse.Success(dataDummy))

        assertNotNull(apiResponse)
        assertTrue(apiResponse is ApiResponse.Success)
        when (apiResponse) {
            is ApiResponse.Success -> {
                assertEquals(dataDummy, apiResponse.data)
            }
        }
    }

    @Test
    fun get_detail_tv_response_success() {
        val dataDummy = DummyDataResponse.tvDetailResponse()
        val idData = dataDummy.id
        `when`(apiServiceTheMovieDB.getDataTvById(idData)).thenReturn(callTvDetailResponse)

        doAnswer {
            (it.arguments[0] as Callback<TvDetailResponse>)
                .onResponse(callTvDetailResponse, Response.success(dataDummy))
        }.`when`(callTvDetailResponse)?.enqueue(any())

        val apiResponse = getValue(remoteDataSource.getDataTvById(idData))

        verify(apiServiceTheMovieDB).getDataTvById(idData)
        verify(apiServiceTheMovieDB.getDataTvById(idData))?.enqueue(any())

        remoteDataSource.getDataTvById(idData).observeForever(observerApiResponseTvDetail)
        verify(observerApiResponseTvDetail).onChanged(ApiResponse.Success(dataDummy))

        assertNotNull(apiResponse)
        assertTrue(apiResponse is ApiResponse.Success)
        when (apiResponse) {
            is ApiResponse.Success -> {
                assertEquals(dataDummy, apiResponse.data)
            }
        }
    }
}