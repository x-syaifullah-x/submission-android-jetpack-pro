package id.xxx.submission.ui.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import id.xxx.submission.ui.main.movie.MovieFragment
import id.xxx.submission.ui.main.tv.TvFragment
import id.xxx.submission.viewmodel.ViewModelKey

@Module
abstract class MainActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    protected abstract fun homeViewModel(tvViewModel: MainViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun contributeMainActivityModule(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeTvFragment(): TvFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieFragment(): MovieFragment
}