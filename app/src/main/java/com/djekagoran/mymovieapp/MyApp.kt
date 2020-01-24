package com.djekagoran.mymovieapp

import androidx.appcompat.app.AppCompatDelegate
import com.djekagoran.mymovieapp.data.repository.SharedPrefView
import com.djekagoran.mymovieapp.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class MyApp : DaggerApplication() {

    @Inject
    lateinit var sharedPref: SharedPrefView

    override fun onCreate() {
        super.onCreate()

        if (sharedPref.loadIsNight()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder()
            .application(this)
            .build()
    }

}