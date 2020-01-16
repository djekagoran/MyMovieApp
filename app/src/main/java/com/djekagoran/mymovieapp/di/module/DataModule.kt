package com.djekagoran.mymovieapp.di.module

import com.djekagoran.mymovieapp.data.AppDataManager
import com.djekagoran.mymovieapp.data.DataManager
import com.djekagoran.mymovieapp.data.api.APIInterface
import com.djekagoran.mymovieapp.data.local.room.CrewDao
import com.djekagoran.mymovieapp.data.local.room.MovieDao
import com.djekagoran.mymovieapp.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class, LocalModule::class])
class DataModule {

    @Provides
    @ApplicationScope
    fun getAppDataManager(apiInterface: APIInterface, movieDao: MovieDao, actorDao: CrewDao): DataManager {
        return AppDataManager(apiInterface, movieDao, actorDao)
    }
}
