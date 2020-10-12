package id.xxx.submission.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import id.xxx.submission.data.source.local.entity.detail.tv.TvDetail
import id.xxx.submission.data.source.local.entity.detail.tv.TvDetailGenreEntity
import id.xxx.submission.data.source.local.entity.detail.tv.TvDetailResponseEntity
import id.xxx.submission.data.source.local.entity.discover.tv.*
import id.xxx.submission.data.source.remote.response.TvDetailResponse
import id.xxx.submission.data.source.remote.response.TvResponse
import id.xxx.submission.utils.DataMapper.tvDetailResponseToTvDetailEntity
import id.xxx.submission.utils.DataMapper.tvResponseToTvResponseEntity
import id.xxx.submission.utils.DataMapper.tvResultToTvResultEntity

@Dao
abstract class TvDao :
    BaseDao<TvResponseEntity, TvResultEntity, TvResponseWithResult, TvResultWithGenre, TvGenreEntity, TvDetailResponseEntity, TvDetailGenreEntity>() {

    companion object {
        private fun query(q: String, isFavorite: Boolean = false) =
            "SELECT * FROM TvResultEntity ${if (isFavorite) "WHERE isFavorite = 1" else ""} ORDER BY $q ASC"

        val SORT_BY_NAME = SimpleSQLiteQuery(query("original_name"))
        val SORT_BY_RELEASE = SimpleSQLiteQuery(query("first_air_date"))
        val SORT_FAV_BY_NAME = SimpleSQLiteQuery(query("original_name", true))
        val SORT_FAV_BY_RELEASE = SimpleSQLiteQuery(query("first_air_date", true))
    }

    @Transaction
    @Query("SELECT * FROM TvResponseEntity")
    abstract fun liveTv(): LiveData<TvResponseWithResult>

    @Transaction
    @Query("SELECT * FROM TvResultEntity")
    abstract fun pageTvResult(): DataSource.Factory<Int, TvResultWithGenre>

    @Transaction
    @RawQuery(observedEntities = [TvResultWithGenre::class])
    abstract fun pageTvResult(query: SupportSQLiteQuery): DataSource.Factory<Int, TvResultWithGenre>?

    @Query("SELECT * FROM TvResultEntity WHERE id_tv_result=:id")
    abstract fun tvResult(id: Int): TvResultEntity

    @Transaction
    @Query("SELECT * FROM TvDetailResponseEntity WHERE pk_id_tv_detail_response=:id")
    abstract fun liveTvDetail(id: Int): LiveData<TvDetail>

    @Query("SELECT * FROM TvDetailResponseEntity WHERE pk_id_tv_detail_response=:id")
    abstract fun tvDetail(id: Int): TvDetailResponseEntity

    @Transaction
    @RawQuery(observedEntities = [TvResultWithGenre::class])
    abstract fun pageTvFavorite(query: SupportSQLiteQuery): DataSource.Factory<Int, TvResultWithGenre>?

    fun insertTv(tvResponse: TvResponse) {
        val fk = insertResponse(tvResponseToTvResponseEntity(tvResponse))
        for (tvResult in tvResponse.results) {
            val idInsertTvResult = insertResult(tvResultToTvResultEntity(fk, tvResult))
            tvResult.genre_ids.forEach {
                insertGenre(TvGenreEntity(fk = idInsertTvResult, genre = it))
            }
        }
    }

    fun insertTvDetail(tvDetailResponse: TvDetailResponse) {
        val fk = insertDetailResponse(tvDetailResponseToTvDetailEntity(tvDetailResponse))
        tvDetailResponse.genres.forEach {
            insertDetailGenre(
                TvDetailGenreEntity(
                    genre_code = it.genre_code,
                    fk = fk,
                    name = it.name
                )
            )
        }
    }
}

