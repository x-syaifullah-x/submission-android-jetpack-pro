package id.xxx.submission.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.sqlite.db.SupportSQLiteQuery
import id.xxx.submission.data.Resource

interface ITheMovieRepository<DataResultModel, DataDetailModel> {

    fun <T> toLiveData(dataSourceFactory: () -> DataSource.Factory<Int, T>): LiveData<PagedList<T>> {
        return LivePagedListBuilder(dataSourceFactory.invoke(), config()).build()
    }

    fun getResult(supportSQLiteQuery: SupportSQLiteQuery? = null): LiveData<Resource<PagedList<DataResultModel>>>
    fun getDetail(id: Int): LiveData<Resource<DataDetailModel>>
    fun getFavorite(supportSQLiteQuery: SupportSQLiteQuery? = null): LiveData<Resource<PagedList<DataResultModel>>>
    fun setFavorite(id: Int, isFavorite: Boolean)
}