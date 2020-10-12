package id.xxx.submission.data.source.local

import androidx.sqlite.db.SupportSQLiteQuery
import id.xxx.submission.data.source.local.dao.MovieDao
import id.xxx.submission.data.source.local.dao.MovieDao.Companion.SORT_BY_NAME
import id.xxx.submission.data.source.local.dao.MovieDao.Companion.SORT_FAV_BY_NAME
import id.xxx.submission.data.source.local.entity.detail.movie.MovieDetail
import id.xxx.submission.data.source.local.entity.discover.movie.MovieResponseWithResult
import id.xxx.submission.data.source.local.entity.discover.movie.MovieResultWithGenre
import id.xxx.submission.data.source.remote.response.MovieDetailResponse
import id.xxx.submission.data.source.remote.response.MovieResponse

class LocalMovieDataSource private constructor(private val movieDao: MovieDao) :
    ITheMovieDataSource<MovieResponse, MovieDetailResponse, MovieResponseWithResult, MovieResultWithGenre, MovieDetail> {

    companion object {
        @Volatile
        private var instance: LocalMovieDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalMovieDataSource =
            instance ?: synchronized(this) { instance ?: LocalMovieDataSource(movieDao) }
    }

    override fun getResponse() = movieDao.liveResponse()

    override fun getResult() = movieDao.pageResult()

    override fun getResultRawQuery(supportSQLiteQuery: SupportSQLiteQuery?) =
        movieDao.pageResult(supportSQLiteQuery ?: run { SORT_BY_NAME })

    override fun getDetail(id: Int) = movieDao.liveMovieDetail(id)

    override fun insertResponse(data: MovieResponse) = movieDao.insertMovie(data)

    override fun insertDetailResponse(data: MovieDetailResponse) = movieDao.insertMovieDetail(data)

    override fun updateFavorite(id: Int, isFavorite: Boolean) {
        movieDao.updateDetailFavorite(movieDao.movieDetail(id)
            .apply { this.isFavorite = isFavorite })
        movieDao.updateResultFavorite(movieDao.movieResult(id)
            .apply { this.isFavorite = isFavorite })
    }

    override fun getFavorite(supportSQLiteQuery: SupportSQLiteQuery?) =
        movieDao.pageMovieFavorite(supportSQLiteQuery ?: run { SORT_FAV_BY_NAME })
}