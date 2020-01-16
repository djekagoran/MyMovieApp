package com.djekagoran.mymovieapp.ui.favorite_movie

import com.djekagoran.mymovieapp.data.model.Movie

interface OnListFavoriteAction {
    fun onListFavoriteAction(movie: Movie, isChecked: Boolean)
}
