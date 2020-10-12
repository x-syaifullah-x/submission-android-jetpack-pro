package id.xxx.submission.data.repository

import id.xxx.submission.data.source.local.LocalDataSource
import id.xxx.submission.data.source.remote.RemoteDataSource
import id.xxx.submission.utils.Executors

class TheMovieRepository private constructor(
    remote: RemoteDataSource, local: LocalDataSource, executors: Executors
) {
    val movie = MovieRepository.getInstance(
        executors = executors, remoteData = remote, localData = local.movie
    )
    val tv = TvRepository.getInstance(
        executors = executors, remoteData = remote, localData = local.tv
    )

    companion object {
        @Volatile
        private var instance: TheMovieRepository? = null

        fun getInstance(
            remote: RemoteDataSource, local: LocalDataSource, executors: Executors
        ): TheMovieRepository =
            instance ?: synchronized(this) {
                instance ?: TheMovieRepository(remote, local, executors)
            }
    }
}