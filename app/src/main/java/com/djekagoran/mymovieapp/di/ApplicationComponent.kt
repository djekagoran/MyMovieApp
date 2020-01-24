package com.djekagoran.mymovieapp.di

import android.app.Application
import com.djekagoran.mymovieapp.MyApp
import com.djekagoran.mymovieapp.di.module.*
import com.djekagoran.mymovieapp.di.module.ActivityModule
import com.djekagoran.mymovieapp.di.module.ApplicationModule
import com.djekagoran.mymovieapp.di.module.ViewModelFactoryModule
import com.djekagoran.mymovieapp.di.scope.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        ViewModelFactoryModule::class,
        DataModule::class,
        PicassoModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<MyApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

}