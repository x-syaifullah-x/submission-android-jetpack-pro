package id.xxx.submission.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import id.xxx.submission.App
import id.xxx.submission.data.DataModule
import id.xxx.submission.ui.detail.DetailModule
import id.xxx.submission.ui.main.MainModule
import id.xxx.submission.viewmodel.ViewModelFactoryModule
import javax.inject.Singleton

@Component(
    modules = [
        DataModule::class,
        ViewModelFactoryModule::class,
        MainModule::class,
        DetailModule::class,
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