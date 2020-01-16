package com.djekagoran.mymovieapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.djekagoran.mymovieapp.data.model.Crew
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie

@Database(entities = [Movie::class, Genre::class, Crew::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun crewDao(): CrewDao

}
