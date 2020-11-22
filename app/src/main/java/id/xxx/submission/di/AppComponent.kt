package id.xxx.submission.di

import dagger.Component
import id.xxx.submission.data.di.DataComponent
import id.xxx.submission.ui.detail.DetailActivity
import id.xxx.submission.ui.main.MainActivity
import id.xxx.submission.ui.main.favorite.FavoriteFragment
import id.xxx.submission.ui.main.favorite.FavoriteMovieFragment
import id.xxx.submission.ui.main.favorite.FavoriteTvFragment
import id.xxx.submission.ui.main.movie.MovieFragment
import id.xxx.submission.ui.main.tv.TvFragment
import id.xxx.submission.viewmodel.ViewModelModule

@AppScope
@Component(
    dependencies = [DataComponent::class],
    modules = [
        ViewModelModule::class,
    ]
)

interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(dataComponent: DataComponent): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: DetailActivity)

    fun inject(fragment: MovieFragment)
    fun inject(fragment: TvFragment)
    fun inject(fragment: FavoriteFragment)
    fun inject(fragment: FavoriteMovieFragment)
    fun inject(fragment: FavoriteTvFragment)
}