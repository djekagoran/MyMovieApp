package com.djekagoran.mymovieapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.djekagoran.mymovieapp.R
import com.djekagoran.mymovieapp.base.BaseActivity
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.ui.detail_movie.DetailMovieActivity
import com.djekagoran.mymovieapp.ui.detail_movie.detailmovie.DetailMovieFragment
import com.djekagoran.mymovieapp.ui.main.favorite.FavoriteFragment
import com.djekagoran.mymovieapp.ui.main.popular.PopularFragment
import com.djekagoran.mymovieapp.ui.main.search.SearchFragment
import com.djekagoran.mymovieapp.ui.main.top_rated.TopRatedFragment
import com.djekagoran.mymovieapp.utill.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : BaseActivity(), OnFragmentListInteraction {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomMenuListener)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, PopularFragment.newInstance())
            transaction.commit()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            when (supportFragmentManager.findFragmentById(R.id.container)) {
                is PopularFragment -> bottomNavigationView.menu.getItem(0).isChecked = true
                is TopRatedFragment -> bottomNavigationView.menu.getItem(1).isChecked = true
                is SearchFragment -> bottomNavigationView.menu.getItem(2).isChecked = true
                is FavoriteFragment -> bottomNavigationView.menu.getItem(3).isChecked = true
                else -> println("Fragment removed")
            }
            if (container_movie_detail != null) {
                when (supportFragmentManager.findFragmentById(R.id.container_movie_detail)) {
                    is DetailMovieFragment -> println("O.K.")
                    else -> println("Fragment removed")
                }
            }
        }
    }

    private var bottomMenuListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.navigation_popular -> {
                loadFragment(PopularFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_top_rated -> {
                loadFragment(TopRatedFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                loadFragment(SearchFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                loadFragment(FavoriteFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun loadFragment(fragment: Fragment) {

        if (container_movie_detail != null) {
            container_movie_detail?.visibility = View.GONE
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
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
