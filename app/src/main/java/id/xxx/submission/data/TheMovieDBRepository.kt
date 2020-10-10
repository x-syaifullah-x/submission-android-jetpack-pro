package id.xxx.submission.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import id.xxx.submission.data.source.remote.RemoteDataSource
import id.xxx.submission.data.source.remote.network.ApiResponse
import id.xxx.submission.data.source.remote.response.MovieDetailResponse
import id.xxx.submission.data.source.remote.response.MovieResponse
import id.xxx.submission.data.source.remote.response.TvDetailResponse
import id.xxx.submission.data.source.remote.response.TvResponse

class TheMovieDBRepository private constructor(private val remoteDataSource: RemoteDataSource) {

    companion object {
        @Volatile
        private var instance: TheMovieDBRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): TheMovieDBRepository =
            instance ?: synchronized(this) { instance ?: TheMovieDBRepository(remoteDataSource) }
    }

    private fun <T> result(
        result: MediatorLiveData<Resource<T>>, response: LiveData<ApiResponse<T>>
    ): LiveData<Resource<T>> {
        result.postValue(Resource.Loading())
        result.addSource(response) {
            result.removeSource(response)
            when (it) {
                is ApiResponse.Success -> result.postValue(Resource.Success(it.data))
                is ApiResponse.Empty -> result.postValue(Resource.Empty(it.data))
                is ApiResponse.Error -> result.postValue(Resource.Error(it.errorMessage))
            }
        }
        return result
    }

    fun getDataMovie(): LiveData<Resource<MovieResponse>> {
        return result(
            MediatorLiveData<Resource<MovieResponse>>(),
            remoteDataSource.getDataMovie()
        )
    }

    fun getDataTv(): LiveData<Resource<TvResponse>> {
        return result(
            MediatorLiveData<Resource<TvResponse>>(),
            remoteDataSource.getDataTv()
        )
    }

    fun getDataTvById(id: Int): LiveData<Resource<TvDetailResponse>> {
        return result(
            MediatorLiveData<Resource<TvDetailResponse>>(),
            remoteDataSource.getDataTvById(id)
        )
    }

    fun getDataMovieById(id: Int): LiveData<Resource<MovieDetailResponse>> {
        return result(
            MediatorLiveData<Resource<MovieDetailResponse>>(),
            remoteDataSource.getDataMovieById(id)
        )
    }
}