package id.xxx.submission.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import id.xxx.submission.data.source.local.entity.detail.movie.MovieDetail
import id.xxx.submission.data.source.local.entity.detail.movie.MovieDetailGenreEntity
import id.xxx.submission.data.source.local.entity.detail.movie.MovieDetailResponseEntity
import id.xxx.submission.data.source.local.entity.discover.movie.*
import id.xxx.submission.data.source.remote.response.MovieDetailResponse
import id.xxx.submission.data.source.remote.response.MovieResponse
import id.xxx.submission.utils.DataMapper.movieDetailResponseToMovieDetailResponseEntity
import id.xxx.submission.utils.DataMapper.movieResponseToMovieResponseEntity
import id.xxx.submission.utils.DataMapper.movieResultToMovieResultEntity

@Dao
abstract class MovieDao :
    BaseDao<MovieResponseEntity, MovieResultEntity, MovieResponseWithResult, MovieResultWithGenre, MovieGenreEntity, MovieDetailResponseEntity, MovieDetailGenreEntity>() {

    companion object {
        private fun query(q: String, isFavorite: Boolean = false) =
            "SELECT * FROM MovieResultEntity ${if (isFavorite) "WHERE isFavorite = 1" else ""} ORDER BY $q ASC"

        val SORT_BY_NAME = SimpleSQLiteQuery(query("title"))
        val SORT_BY_RELEASE = SimpleSQLiteQuery(query("release_date"))
        val SORT_FAV_BY_NAME = SimpleSQLiteQuery(query("title", true))
        val SORT_FAV_BY_RELEASE = SimpleSQLiteQuery(query("release_date", true))
    }

    @Transaction
    @Query("SELECT * FROM MovieResponseEntity")
    abstract fun liveResponse(): LiveData<MovieResponseWithResult>

    @Transaction
    @Query("SELECT * FROM MovieResultEntity")
    abstract fun pageResult(): DataSource.Factory<Int, MovieResultWithGenre>

    @Transaction
    @RawQuery(observedEntities = [MovieResultWithGenre::class])
    abstract fun pageResult(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieResultWithGenre>?

    @Query("SELECT * FROM MovieResultEntity WHERE id_movie_result=:id")
    abstract fun movieResult(id: Int): MovieResultEntity

    @Query("SELECT * FROM MovieDetailResponseEntity WHERE pk_movie_detail_response=:id")
    abstract fun movieDetail(id: Int): MovieDetailResponseEntity

    @Transaction
    @Query("SELECT * FROM MovieDetailResponseEntity WHERE pk_movie_detail_response=:id")
    abstract fun liveMovieDetail(id: Int): LiveData<MovieDetail>

    @Transaction
    @RawQuery(observedEntities = [MovieResultWithGenre::class])
    abstract fun pageMovieFavorite(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieResultWithGenre>?

    fun insertMovie(movieResponse: MovieResponse) {
        val idInsertResponse =
            insertResponse(movieResponseToMovieResponseEntity(movieResponse))
        for (movieResult in movieResponse.results) {
            val idInsertMovieResult =
                insertResult(movieResultToMovieResultEntity(idInsertResponse, movieResult))
            movieResult.genre_ids?.forEach {
                insertGenre(MovieGenreEntity(fk = idInsertMovieResult, genre = it))
            }
        }
    }

    fun insertMovieDetail(movieDetailResponse: MovieDetailResponse) {
        val idResult =
            insertDetailResponse(movieDetailResponseToMovieDetailResponseEntity(movieDetailResponse))
        movieDetailResponse.genres.forEach {
            insertDetailGenre(
                MovieDetailGenreEntity(
                    genre_code = it.genre_code, fk = idResult, name = it.name
                )
            )
        }
    }
}