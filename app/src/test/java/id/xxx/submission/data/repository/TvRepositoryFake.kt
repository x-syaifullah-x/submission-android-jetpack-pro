package id.xxx.submission.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.sqlite.db.SupportSQLiteQuery
import id.xxx.submission.data.model.TvDetailModel
import id.xxx.submission.data.model.TvResultModel
import id.xxx.submission.data.source.NetworkBoundResource
import id.xxx.submission.data.source.local.LocalTvDataSource
import id.xxx.submission.data.source.remote.RemoteDataSource
import id.xxx.submission.data.source.remote.network.ApiResponse
import id.xxx.submission.data.source.remote.response.TvDetailResponse
import id.xxx.submission.data.source.remote.response.TvResponse
import id.xxx.submission.utils.DataMapper
import id.xxx.submission.utils.Executors

class TvRepositoryFake constructor(
    private val executors: Executors,
    private val localData: LocalTvDataSource,
    private val remoteData: RemoteDataSource
) : ITheMovieRepository<TvResultModel, TvDetailModel> {

    override fun getResult(supportSQLiteQuery: SupportSQLiteQuery?) =
        object : NetworkBoundResource<PagedList<TvResultModel>, TvResponse>(executors) {
            override fun loadFromDB(): LiveData<PagedList<TvResultModel>> {
                val result = localData.getResultRawQuery(supportSQLiteQuery)
                val convert =
                    result?.mapByPage { DataMapper.listTvResultWithGenreToListTvResultModel(it) }
                return convert?.let {
                    LivePagedListBuilder(it, config()).build()
                } ?: MutableLiveData()
            }

            override fun shouldFetch(data: PagedList<TvResultModel>?) =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<TvResponse>> =
                remoteData.getTv()

            override fun saveCallResult(data: TvResponse) {
                localData.insertResponse(data)
            }
        }.asLiveData()

    override fun getDetail(id: Int) =
        object : NetworkBoundResource<TvDetailModel, TvDetailResponse>(executors) {
            override fun loadFromDB(): LiveData<TvDetailModel> =
                Transformations.map(localData.getDetail(id)) {
                    DataMapper.tvDetailToTvDetailModel(it)
                }

            override fun shouldFetch(data: TvDetailModel?) = data == null

            override fun createCall(): LiveData<ApiResponse<TvDetailResponse>> =
                remoteData.getTvDetail(id)

            override fun saveCallResult(data: TvDetailResponse) =
                localData.insertDetailResponse(data)

        }.asLiveData()

    override fun getFavorite(supportSQLiteQuery: SupportSQLiteQuery?) =
        object : NetworkBoundResource<PagedList<TvResultModel>, TvResponse>(executors) {
            override fun loadFromDB(): LiveData<PagedList<TvResultModel>> {
                val result = localData.getFavorite(supportSQLiteQuery)
                val convert =
                    result?.mapByPage { DataMapper.listTvResultWithGenreToListTvResultModel(it) }
                return convert?.let {
                    LivePagedListBuilder(it, config()).build()
                } ?: MutableLiveData()
            }

            override fun shouldFetch(data: PagedList<TvResultModel>?) = false

            override fun createCall(): LiveData<ApiResponse<TvResponse>> =
                remoteData.getTv()

            override fun saveCallResult(data: TvResponse) {}
        }.asLiveData()

    override fun setFavorite(id: Int, isFavorite: Boolean) {
        executors.diskIO().execute {
            localData.updateFavorite(id, isFavorite)
        }
    }
}