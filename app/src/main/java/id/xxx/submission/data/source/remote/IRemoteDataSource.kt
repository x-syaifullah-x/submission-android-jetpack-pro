package id.xxx.submission.data.source.remote

import androidx.lifecycle.LiveData
import id.xxx.submission.data.source.remote.network.ApiResponse
import id.xxx.submission.data.source.remote.response.MovieDetailResponse
import id.xxx.submission.data.source.remote.response.MovieResponse
import id.xxx.submission.data.source.remote.response.TvDetailResponse
import id.xxx.submission.data.source.remote.response.TvResponse

interface IRemoteDataSource {
    fun getDataMovie(): LiveData<ApiResponse<MovieResponse>>
    fun getDataTv(): LiveData<ApiResponse<TvResponse>>
    fun getDataMovieById(id: Int): LiveData<ApiResponse<MovieDetailResponse>>
    fun getDataTvById(id: Int): LiveData<ApiResponse<TvDetailResponse>>
}