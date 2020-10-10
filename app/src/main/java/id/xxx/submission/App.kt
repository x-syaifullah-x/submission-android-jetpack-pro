package id.xxx.submission

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import id.xxx.submission.di.DaggerAppComponent

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
        return DaggerAppComponent.builder().application(this).build()
    }
}