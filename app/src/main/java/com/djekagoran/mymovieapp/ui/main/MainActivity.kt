package com.djekagoran.mymovieapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.djekagoran.mymovieapp.R
import com.djekagoran.mymovieapp.base.BaseActivity
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.ui.detail_movie.DetailMovieActivity
import com.djekagoran.mymovieapp.ui.detail_movie.detailmovie.DetailMovieFragment
import com.djekagoran.mymovieapp.utill.Constants
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : BaseActivity(), OnFragmentListInteraction {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nav = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        NavigationUI.setupWithNavController(bottomNavigationView, nav.navController)

    }

    override fun onListFragmentInteraction(movie: Movie, list_genre: ArrayList<Genre>) {
        if (!displayDetailFragment(movie, list_genre)) {
            val intent = Intent(this, DetailMovieActivity::class.java)
            intent.putExtra(Constants.MOVIE, movie)
            intent.putExtra(Constants.LIST_GENRE, list_genre)
            startActivity(intent)
        }
    }

    override fun onListLoadDisplayFirstMovie(movie: Movie, list_genre: ArrayList<Genre>) {
        displayDetailFragment(movie, list_genre)
    }

    private fun displayDetailFragment(movie: Movie, list_genre: ArrayList<Genre>): Boolean {
        if (container_movie_detail != null) {
            container_movie_detail?.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_movie_detail, DetailMovieFragment.newInstance(movie, list_genre))
                .commit()
            return true
        }
        return false
    }

}
