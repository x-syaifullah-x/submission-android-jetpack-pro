package id.xxx.submission.data.source.local

import id.xxx.submission.data.source.local.dao.MovieDao
import id.xxx.submission.data.source.local.dao.TvDao

class LocalDataSource private constructor(tvDao: TvDao, movieDao: MovieDao) {

    val movie = LocalMovieDataSource.getInstance(movieDao)
    val tv = LocalTvDataSource.getInstance(tvDao)

    companion object {
        @Volatile
        private var instance: LocalDataSource? = null

        fun getInstance(tvDao: TvDao, movieDao: MovieDao): LocalDataSource =
            instance ?: synchronized(this) { instance ?: LocalDataSource(tvDao, movieDao) }
    }
}