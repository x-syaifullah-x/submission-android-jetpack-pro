package id.xxx.submission.data.source.local

import androidx.sqlite.db.SupportSQLiteQuery
import id.xxx.submission.data.source.local.dao.TvDao
import id.xxx.submission.data.source.local.dao.TvDao.Companion.SORT_BY_NAME
import id.xxx.submission.data.source.local.dao.TvDao.Companion.SORT_FAV_BY_NAME
import id.xxx.submission.data.source.local.entity.detail.tv.TvDetail
import id.xxx.submission.data.source.local.entity.discover.tv.TvResponseWithResult
import id.xxx.submission.data.source.local.entity.discover.tv.TvResultWithGenre
import id.xxx.submission.data.source.remote.response.TvDetailResponse
import id.xxx.submission.data.source.remote.response.TvResponse

class LocalTvDataSource private constructor(private val tvDao: TvDao) :
    ITheMovieDataSource<TvResponse, TvDetailResponse, TvResponseWithResult, TvResultWithGenre, TvDetail> {

    companion object {
        @Volatile
        private var instance: LocalTvDataSource? = null

        fun getInstance(tvDao: TvDao): LocalTvDataSource =
            instance ?: synchronized(this) { instance ?: LocalTvDataSource(tvDao) }
    }

    override fun getResponse() = tvDao.liveTv()

    override fun getResult() = tvDao.pageTvResult()

    override fun getResultRawQuery(supportSQLiteQuery: SupportSQLiteQuery?) =
        tvDao.pageTvResult(supportSQLiteQuery ?: run { SORT_BY_NAME })

    override fun getDetail(id: Int) = tvDao.liveTvDetail(id)

    override fun insertResponse(data: TvResponse) = tvDao.insertTv(data)

    override fun insertDetailResponse(data: TvDetailResponse) = tvDao.insertTvDetail(data)

    override fun updateFavorite(id: Int, isFavorite: Boolean) {
        tvDao.updateDetailFavorite(tvDao.tvDetail(id).apply { this.isFavorite = isFavorite })
        tvDao.updateResultFavorite(tvDao.tvResult(id).apply { this.isFavorite = isFavorite })
    }

    override fun getFavorite(supportSQLiteQuery: SupportSQLiteQuery?) =
        tvDao.pageTvFavorite(supportSQLiteQuery ?: run { SORT_FAV_BY_NAME })
}