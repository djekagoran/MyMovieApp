package com.djekagoran.mymovieapp.di.module

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.djekagoran.mymovieapp.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class SharedPrefModule {

    private val PREF_IT = "prefit"

    @Provides
    @ApplicationScope
    fun getSharedPreferences(context: Application): SharedPreferences {
        return context.getSharedPreferences(PREF_IT, MODE_PRIVATE)
    }
}
