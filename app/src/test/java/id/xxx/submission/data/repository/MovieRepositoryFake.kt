package id.xxx.submission.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.sqlite.db.SupportSQLiteQuery
import id.xxx.submission.data.model.MovieDetailModel
import id.xxx.submission.data.model.MovieResultModel
import id.xxx.submission.data.source.NetworkBoundResource
import id.xxx.submission.data.source.local.LocalMovieDataSource
import id.xxx.submission.data.source.remote.RemoteDataSource
import id.xxx.submission.data.source.remote.network.ApiResponse
import id.xxx.submission.data.source.remote.response.MovieDetailResponse
import id.xxx.submission.data.source.remote.response.MovieResponse
import id.xxx.submission.utils.DataMapper
import id.xxx.submission.utils.DataMapper.movieDetailToMovieDetailModel
import id.xxx.submission.utils.Executors

class MovieRepositoryFake(
    private val executors: Executors,
    private val localData: LocalMovieDataSource,
    private val remoteData: RemoteDataSource
) : ITheMovieRepository<MovieResultModel, MovieDetailModel> {

    override fun getResult(supportSQLiteQuery: SupportSQLiteQuery?) =
        object : NetworkBoundResource<PagedList<MovieResultModel>, MovieResponse>(executors) {
            override fun loadFromDB(): LiveData<PagedList<MovieResultModel>> {
                val result = localData.getResultRawQuery(supportSQLiteQuery)
                val convert =
                    result?.mapByPage { DataMapper.listMovieResultWithGenreToListMovieResultModel(it) }
                return convert?.let {
                    LivePagedListBuilder(it, config()).build()
                } ?: MutableLiveData()
            }

            override fun shouldFetch(data: PagedList<MovieResultModel>?) =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> =
                remoteData.getMovie()

            override fun saveCallResult(data: MovieResponse) {
                localData.insertResponse(data)
            }
        }.asLiveData()

    override fun getDetail(id: Int) =
        object : NetworkBoundResource<MovieDetailModel, MovieDetailResponse>(executors) {
            override fun loadFromDB(): LiveData<MovieDetailModel> =
                map(localData.getDetail(id)) { movieDetailToMovieDetailModel(it) }

            override fun shouldFetch(data: MovieDetailModel?): Boolean = (data == null)

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> =
                remoteData.getMovieDetail(id)

            override fun saveCallResult(data: MovieDetailResponse) =
                localData.insertDetailResponse(data)
        }.asLiveData()

    override fun getFavorite(supportSQLiteQuery: SupportSQLiteQuery?) =
        object : NetworkBoundResource<PagedList<MovieResultModel>, MovieResponse>(executors) {
            override fun loadFromDB(): LiveData<PagedList<MovieResultModel>> {
                val result = localData.getFavorite(supportSQLiteQuery)
                val convert =
                    result?.mapByPage { DataMapper.listMovieResultWithGenreToListMovieResultModel(it) }
                return convert?.let {
                    LivePagedListBuilder(it, config()).build()
                } ?: MutableLiveData()
            }

            override fun shouldFetch(data: PagedList<MovieResultModel>?) = false

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> =
                remoteData.getMovie()

            override fun saveCallResult(data: MovieResponse) {}
        }.asLiveData()

    override fun setFavorite(id: Int, isFavorite: Boolean) {
        executors.diskIO().execute {
            localData.updateFavorite(id, isFavorite)
        }
    }
}