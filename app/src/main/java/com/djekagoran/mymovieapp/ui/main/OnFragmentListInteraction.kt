package com.djekagoran.mymovieapp.ui.main

import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import java.util.ArrayList

interface OnFragmentListInteraction {
    fun onListFragmentInteraction(movie: Movie, list_genre: ArrayList<Genre>)
    fun onListLoadDisplayFirstMovie(movie: Movie, list_genre: ArrayList<Genre>) {}
}
