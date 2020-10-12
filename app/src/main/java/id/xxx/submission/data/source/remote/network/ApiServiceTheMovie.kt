package id.xxx.submission.data.source.remote.network

import id.xxx.submission.BuildConfig.API_KEY
import id.xxx.submission.data.source.remote.response.MovieDetailResponse
import id.xxx.submission.data.source.remote.response.MovieResponse
import id.xxx.submission.data.source.remote.response.TvDetailResponse
import id.xxx.submission.data.source.remote.response.TvResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceTheMovie {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org"
    }

    @GET("3/discover/movie?api_key=$API_KEY")
    fun getMovie(@Query("language") language: String? = "en-US"): Call<MovieResponse>?

    @GET("3/discover/tv?api_key=${API_KEY}")
    fun getTv(@Query("language") language: String? = "en-US"): Call<TvResponse>?

    @GET("3/movie/{id}?api_key=${API_KEY}")
    fun getMovieDetail(
        @Path("id") id: Int, @Query("language") language: String? = "en-US",
    ): Call<MovieDetailResponse>

    @GET("3/tv/{id}?api_key=${API_KEY}")
    fun getTvDetail(
        @Path("id") id: Int, @Query("language") language: String? = "en-US",
    ): Call<TvDetailResponse>
}