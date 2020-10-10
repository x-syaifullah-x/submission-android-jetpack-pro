package id.xxx.submission.di.module

import dagger.Module
import dagger.Provides
import id.xxx.submission.data.TheMovieDBRepository
import id.xxx.submission.data.source.remote.RemoteDataSource
import id.xxx.submission.data.source.remote.network.ApiConfig
import id.xxx.submission.data.source.remote.network.ApiServiceTheMovieDB
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    internal fun provideApiServiceTheMovieDB(): ApiServiceTheMovieDB {
        return ApiConfig.service(ApiServiceTheMovieDB.BASE_URL, ApiServiceTheMovieDB::class.java)
    }

    @Provides
    @Singleton
    internal fun provideRemoteDataSource(apiServiceTheMovieDB: ApiServiceTheMovieDB): RemoteDataSource {
        return RemoteDataSource.getInstance(apiServiceTheMovieDB)
    }

    @Provides
    @Singleton
    internal fun provideTheMovieDbRepository(remoteDataSource: RemoteDataSource): TheMovieDBRepository {
        return TheMovieDBRepository.getInstance(remoteDataSource)
    }
}