package com.djekagoran.mymovieapp.data.local

import androidx.lifecycle.LiveData
import com.djekagoran.mymovieapp.data.model.Crew
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import kotlinx.coroutines.flow.Flow

import java.util.ArrayList

interface DbHelperView {

    val popular: Flow<List<Movie>>
    val topRated: Flow<List<Movie>>
    val genres: Flow<List<Genre>>

    suspend fun savePopular(list: ArrayList<Movie>)
    suspend fun saveTopRated(list: ArrayList<Movie>)
    suspend fun saveGenre(list: List<Genre>)

    val favoriteMovie: LiveData<List<Movie>>
    val favoriteCrew: LiveData<List<Crew>>
    val recentlyMovie: LiveData<List<Movie>>

    suspend fun saveFavoriteMovie(m: Movie)
    suspend fun saveFavoriteCrew(m: Crew)
    suspend fun saveRecently(m: Movie)

    suspend fun deleteFavoriteMovie(m: Movie)
    suspend fun deleteFavoriteCrew(m: Crew)

    fun countFavoriteMovie(m: Movie): Flow<Int>
    fun countFavoriteCrew(m: Crew): Flow<Int>

}
