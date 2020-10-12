package id.xxx.submission.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.xxx.submission.data.source.remote.network.ApiResponse
import id.xxx.submission.data.source.remote.network.ApiServiceTheMovie
import id.xxx.submission.data.source.remote.response.MovieDetailResponse
import id.xxx.submission.data.source.remote.response.MovieResponse
import id.xxx.submission.data.source.remote.response.TvDetailResponse
import id.xxx.submission.data.source.remote.response.TvResponse
import id.xxx.submission.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiServiceTheMovie: ApiServiceTheMovie) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiServiceTheMovieDB: ApiServiceTheMovie): RemoteDataSource =
            instance ?: synchronized(this) { instance ?: RemoteDataSource(apiServiceTheMovieDB) }
    }

    private fun <T> enqueueCallback(mutableLiveData: MutableLiveData<ApiResponse<T>>): Callback<T?> {
        EspressoIdlingResource.increment()
        return object : Callback<T?> {
            override fun onResponse(call: Call<T?>, response: Response<T?>) {
                val data = response.body()
                mutableLiveData.postValue(
                    if (data != null) ApiResponse.Success(data) else ApiResponse.Empty()
                )
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<T?>, t: Throwable) {
                mutableLiveData.postValue(ApiResponse.Error(t.message.toString()))
                Log.e("RemoteDataSource", t.message.toString())
                EspressoIdlingResource.decrement()
            }
        }
    }

    fun getMovie(): LiveData<ApiResponse<MovieResponse>> {
        val resultData = MutableLiveData<ApiResponse<MovieResponse>>()
        apiServiceTheMovie.getMovie()?.enqueue(enqueueCallback(resultData))
        return resultData
    }

    fun getMovieDetail(id: Int): LiveData<ApiResponse<MovieDetailResponse>> {
        val resultData = MutableLiveData<ApiResponse<MovieDetailResponse>>()
        apiServiceTheMovie.getMovieDetail(id).enqueue(enqueueCallback(resultData))
        return resultData
    }

    fun getTv(): LiveData<ApiResponse<TvResponse>> {
        val resultData = MutableLiveData<ApiResponse<TvResponse>>()
        apiServiceTheMovie.getTv()?.enqueue(enqueueCallback(resultData))
        return resultData
    }

    fun getTvDetail(id: Int): LiveData<ApiResponse<TvDetailResponse>> {
        val resultData = MutableLiveData<ApiResponse<TvDetailResponse>>()
        apiServiceTheMovie.getTvDetail(id).enqueue(enqueueCallback(resultData))
        return resultData
    }
}