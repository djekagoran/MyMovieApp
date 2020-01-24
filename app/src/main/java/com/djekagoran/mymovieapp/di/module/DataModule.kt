package com.djekagoran.mymovieapp.di.module

import android.content.SharedPreferences
import com.djekagoran.mymovieapp.data.repository.AppDataRepository
import com.djekagoran.mymovieapp.data.repository.AppDataView
import com.djekagoran.mymovieapp.data.api.APIInterface
import com.djekagoran.mymovieapp.data.local.room.CrewDao
import com.djekagoran.mymovieapp.data.local.room.MovieDao
import com.djekagoran.mymovieapp.data.repository.SharedPrefRepository
import com.djekagoran.mymovieapp.data.repository.SharedPrefView
import com.djekagoran.mymovieapp.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class, LocalModule::class, SharedPrefModule::class])
class DataModule {

    @Provides
    @ApplicationScope
    fun getAppDataManager(apiInterface: APIInterface, movieDao: MovieDao, actorDao: CrewDao): AppDataView {
        return AppDataRepository(
            apiInterface,
            movieDao,
            actorDao
        )
    }

    @Provides
    @ApplicationScope
    fun getSharedPref(sharedPref: SharedPreferences): SharedPrefView {
        return SharedPrefRepository(sharedPref)
    }
}
