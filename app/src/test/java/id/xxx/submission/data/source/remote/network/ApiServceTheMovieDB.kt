package id.xxx.submission.data.source.remote.network

import id.xxx.submission.data.source.remote.network.ApiServiceTheMovie.Companion.BASE_URL
import id.xxx.submission.data.source.remote.response.MovieDetailResponse
import id.xxx.submission.data.source.remote.response.MovieResponse
import id.xxx.submission.data.source.remote.response.TvDetailResponse
import id.xxx.submission.data.source.remote.response.TvResponse
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class ApiServiceTheMovieDBTest {

    private val apiService = ApiConfig.service(BASE_URL, ApiServiceTheMovie::class.java)
    private val contDownLatch = CountDownLatch(1)

    @Test
    fun api_service_get_data_movie() {
        apiService.getMovie()?.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                contDownLatch.countDown()
                print(response.body()?.results)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                contDownLatch.countDown()
                t.printStackTrace()
            }

        })
        contDownLatch.await(ApiConfig.TIME_OUT, TimeUnit.SECONDS)
    }

    @Test
    fun api_service_get_data_tv() {
        apiService.getTv()?.enqueue(object : Callback<TvResponse> {
            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                contDownLatch.countDown()
                print(response.body()?.results)
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
        contDownLatch.await(ApiConfig.TIME_OUT, TimeUnit.SECONDS)
    }

    @Test
    fun api_service_get_data_movie_by_id() {
        apiService.getMovieDetail(5).enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(
                call: Call<MovieDetailResponse>, response: Response<MovieDetailResponse>
            ) {
                contDownLatch.countDown()
                print(response.body())
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
        contDownLatch.await(ApiConfig.TIME_OUT, TimeUnit.SECONDS)
    }

    @Test
    fun api_service_get_data_tv_by_id() {
        apiService.getTvDetail(76479).enqueue(object : Callback<TvDetailResponse> {
            override fun onResponse(
                call: Call<TvDetailResponse>, response: Response<TvDetailResponse>
            ) {
                contDownLatch.countDown()
                print(response.body())
            }

            override fun onFailure(call: Call<TvDetailResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
        contDownLatch.await(ApiConfig.TIME_OUT, TimeUnit.SECONDS)
    }
}