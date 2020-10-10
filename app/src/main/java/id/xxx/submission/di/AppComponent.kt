package id.xxx.submission.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import id.xxx.submission.App
import id.xxx.submission.di.module.ApiModule
import id.xxx.submission.ui.detail.DetailActivityModule
import id.xxx.submission.ui.main.MainActivityModule
import id.xxx.submission.viewmodel.ViewModelFactoryModule
import javax.inject.Singleton

@Component(
    modules = [
        ApiModule::class,
        ViewModelFactoryModule::class,
        MainActivityModule::class,
        DetailActivityModule::class,
        AndroidSupportInjectionModule::class
    ]
)

@Singleton
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}