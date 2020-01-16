package com.djekagoran.mymovieapp.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
internal abstract class ApplicationModule {

    @Binds
    abstract fun bindContext(application: Application): Context

}