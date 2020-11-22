package id.xxx.submission

import android.app.Application
import id.xxx.submission.data.di.DaggerDataComponent
import id.xxx.submission.data.di.DataComponent
import id.xxx.submission.di.AppComponent
import id.xxx.submission.di.DaggerAppComponent

class App : Application() {
    private val dataComponent: DataComponent by lazy {
        DaggerDataComponent.factory().create(this)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(dataComponent)
    }
}