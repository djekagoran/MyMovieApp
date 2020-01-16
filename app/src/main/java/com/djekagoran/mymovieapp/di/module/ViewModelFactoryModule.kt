package com.djekagoran.mymovieapp.di.module

import androidx.lifecycle.ViewModelProvider
import com.djekagoran.mymovieapp.view_models.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
internal abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}