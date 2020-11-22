package id.xxx.submission.data.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import id.xxx.submission.data.di.module.DataModule
import id.xxx.submission.data.repository.MovieRepository
import id.xxx.submission.data.repository.TvRepository
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class
    ]
)
interface DataComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): DataComponent
    }

    fun provideMovieRepo(): MovieRepository
    fun provideTvRepo(): TvRepository
}