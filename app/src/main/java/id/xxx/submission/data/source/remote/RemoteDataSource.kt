package id.xxx.submission.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.xxx.submission.data.source.remote.network.ApiResponse
import id.xxx.submission.data.source.remote.network.ApiServiceTheMovieDB
import id.xxx.submission.data.source.remote.response.MovieDetailResponse
import id.xxx.submission.data.source.remote.response.MovieResponse
import id.xxx.submission.data.source.remote.response.TvDetailResponse
import id.xxx.submission.data.source.remote.response.TvResponse
import id.xxx.submission.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(
    private val apiServiceTheMovieDB: ApiServiceTheMovieDB
) : IRemoteDataSource {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiServiceTheMovieDB: ApiServiceTheMovieDB): RemoteDataSource =
            instance ?: synchronized(this) { instance ?: RemoteDataSource(apiServiceTheMovieDB) }
    }

    override fun getDataMovie(): LiveData<ApiResponse<MovieResponse>> {
        val data = MutableLiveData<ApiResponse<MovieResponse>>()
        apiServiceTheMovieDB.getDataMovie()?.enqueue(enqueueCallback(data))
        return data
    }

    override fun getDataTv(): LiveData<ApiResponse<TvResponse>> {
        val data = MutableLiveData<ApiResponse<TvResponse>>()
        apiServiceTheMovieDB.getDataTv()?.enqueue(enqueueCallback(data))
        return data
    }

    override fun getDataMovieById(id: Int): LiveData<ApiResponse<MovieDetailResponse>> {
        val data = MutableLiveData<ApiResponse<MovieDetailResponse>>()
        apiServiceTheMovieDB.getDataMovieById(id).enqueue(enqueueCallback(data))
        return data
    }

    override fun getDataTvById(id: Int): LiveData<ApiResponse<TvDetailResponse>> {
        val data = MutableLiveData<ApiResponse<TvDetailResponse>>()
        apiServiceTheMovieDB.getDataTvById(id).enqueue(enqueueCallback(data))
        return data
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
                mutableLiveData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
                EspressoIdlingResource.decrement()
            }
        }
    }
}