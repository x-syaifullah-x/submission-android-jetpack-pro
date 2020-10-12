package id.xxx.submission.ui.detail

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import id.xxx.submission.viewmodel.ViewModelKey

@Module
abstract class DetailModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    protected abstract fun detailViewModel(viewModel: DetailViewModel): ViewModel

    @ContributesAndroidInjector()
    abstract fun contributeDetailActivity(): DetailActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailMovieFragment(): DetailMovieFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailTvFragment(): DetailTvFragment
}