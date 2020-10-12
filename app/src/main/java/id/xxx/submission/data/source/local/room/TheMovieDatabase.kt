package id.xxx.submission.data.source.local.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.xxx.submission.data.source.local.dao.MovieDao
import id.xxx.submission.data.source.local.dao.TvDao
import id.xxx.submission.data.source.local.entity.detail.movie.MovieDetailGenreEntity
import id.xxx.submission.data.source.local.entity.detail.movie.MovieDetailResponseEntity
import id.xxx.submission.data.source.local.entity.detail.tv.TvDetailGenreEntity
import id.xxx.submission.data.source.local.entity.detail.tv.TvDetailResponseEntity
import id.xxx.submission.data.source.local.entity.discover.movie.MovieGenreEntity
import id.xxx.submission.data.source.local.entity.discover.movie.MovieResponseEntity
import id.xxx.submission.data.source.local.entity.discover.movie.MovieResultEntity
import id.xxx.submission.data.source.local.entity.discover.tv.TvGenreEntity
import id.xxx.submission.data.source.local.entity.discover.tv.TvResponseEntity
import id.xxx.submission.data.source.local.entity.discover.tv.TvResultEntity

@Database(
    entities = [
        MovieResponseEntity::class,
        MovieResultEntity::class,
        MovieGenreEntity::class,
        MovieDetailResponseEntity::class,
        MovieDetailGenreEntity::class,

        TvResponseEntity::class,
        TvResultEntity::class,
        TvGenreEntity::class,
        TvDetailResponseEntity::class,
        TvDetailGenreEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class TheMovieDatabase : RoomDatabase() {
    abstract fun theMovieDbDao(): TvDao
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: TheMovieDatabase? = null

        fun getDatabase(application: Application): TheMovieDatabase {
            if (INSTANCE == null) {
                synchronized(TheMovieDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            application, TheMovieDatabase::class.java, "database"
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return INSTANCE as TheMovieDatabase
        }
    }
}