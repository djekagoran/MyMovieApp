package com.djekagoran.mymovieapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllGenre(genres: List<Genre>)

    @Query("DELETE FROM movie_table WHERE movie_type = :type")
    suspend fun deleteAllMoviesByType(type: String)

    @Query("SELECT * FROM movie_table WHERE movie_type = :type")
    suspend fun getAllMoviesByType(type: String): List<Movie>

    @Query("SELECT * FROM genre_table")
    suspend fun getAllGenres(): List<Genre>


    @Query("SELECT * FROM movie_table WHERE movie_type = :type ORDER BY create_time DESC")
    fun getFavoriteMovies(type: String): LiveData<List<Movie>>

    @Query("SELECT * FROM movie_table WHERE movie_type = :type ORDER BY create_time DESC")
    fun getRecentlyMovies(type: String): LiveData<List<Movie>>

    @Query("SELECT COUNT(*) FROM movie_table WHERE movie_type = :type AND id = :id")
    suspend fun countFavorite(type: String, id: Int): Int

}
