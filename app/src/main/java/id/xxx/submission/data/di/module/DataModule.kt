package id.xxx.submission.data.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import id.xxx.submission.data.repository.MovieRepository
import id.xxx.submission.data.repository.TheMovieRepository
import id.xxx.submission.data.repository.TvRepository
import id.xxx.submission.data.source.local.LocalDataSource
import id.xxx.submission.data.source.local.room.TheMovieDatabase
import id.xxx.submission.data.source.remote.RemoteDataSource
import id.xxx.submission.data.source.remote.network.ApiConfig
import id.xxx.submission.data.source.remote.network.ApiServiceTheMovie
import id.xxx.submission.utils.Executors
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    internal fun provideApiServiceTheMovie() =
        ApiConfig.service(ApiServiceTheMovie.BASE_URL, ApiServiceTheMovie::class.java)

    @Provides
    @Singleton
    internal fun provideRemoteDataSource(apiService: ApiServiceTheMovie) =
        RemoteDataSource.getInstance(apiService)

    @Provides
    @Singleton
    internal fun provideTheMovieDatabase(application: Application) =
        TheMovieDatabase.getDatabase(application)

    @Provides
    @Singleton
    internal fun provideLocalDataSource(database: TheMovieDatabase) =
        LocalDataSource.getInstance(database.theMovieDbDao(), database.movieDao())

    @Provides
    @Singleton
    internal fun provideTheMovieRepository(
        remote: RemoteDataSource, local: LocalDataSource
    ) = TheMovieRepository.getInstance(remote, local, Executors())

    @Provides
    @Singleton
    internal fun provideMovieRepository(
        remote: RemoteDataSource, local: LocalDataSource
    ) = MovieRepository.getInstance(Executors(), remote, local.movie)

    @Provides
    @Singleton
    internal fun provideTvRepository(
        remote: RemoteDataSource, local: LocalDataSource
    ) = TvRepository.getInstance(Executors(), remote, local.tv)

}