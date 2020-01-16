package com.djekagoran.mymovieapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.djekagoran.mymovieapp.data.model.Crew

@Dao
interface CrewDao {

    @get:Query("SELECT * FROM actor_table ORDER BY create_time DESC")
    val actors: LiveData<List<Crew>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(crew: Crew)

    @Delete
    suspend fun delete(crew: Crew)

    @Query("SELECT COUNT(*) FROM actor_table WHERE id = :id")
    suspend fun countFavoriteCrew(id: Int): Int

}
