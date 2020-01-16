package com.djekagoran.mymovieapp.ui.recently_movie.recentlymovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.djekagoran.mymovieapp.data.DataManager
import com.djekagoran.mymovieapp.data.model.Movie
import javax.inject.Inject

class RecentlyMovieViewModel @Inject constructor(private val appDataManager: DataManager): ViewModel() {

    private var dataMovies: LiveData<List<Movie>>? = null

    fun getRecentlyMovies(): LiveData<List<Movie>>? {
        dataMovies = appDataManager.recentlyMovie
        return dataMovies
    }

}
