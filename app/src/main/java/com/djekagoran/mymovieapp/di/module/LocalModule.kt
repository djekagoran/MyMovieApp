package com.djekagoran.mymovieapp.di.module

import android.app.Application
import androidx.room.Room
import com.djekagoran.mymovieapp.data.local.room.CrewDao
import com.djekagoran.mymovieapp.data.local.room.MovieDao
import com.djekagoran.mymovieapp.data.local.room.MovieDatabase
import com.djekagoran.mymovieapp.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class LocalModule {

    @Provides
    @ApplicationScope
    fun provideMovieDao(db: MovieDatabase): MovieDao {
        return db.movieDao()
    }

    @Provides
    @ApplicationScope
    fun provideCrewDao(db: MovieDatabase): CrewDao {
        return db.crewDao()
    }

    @Provides
    @ApplicationScope
    fun provideDb(context: Application): MovieDatabase {
        return Room.databaseBuilder(context.applicationContext, MovieDatabase::class.java, "movie_database")
                .fallbackToDestructiveMigration()
                .build()
    }

}
