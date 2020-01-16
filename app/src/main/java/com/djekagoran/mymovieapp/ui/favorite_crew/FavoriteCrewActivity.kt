package com.djekagoran.mymovieapp.ui.favorite_crew

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.djekagoran.mymovieapp.R
import com.djekagoran.mymovieapp.base.BaseActivity
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.ui.detail_movie.DetailMovieActivity
import com.djekagoran.mymovieapp.ui.favorite_crew.favoritecrew.FavoriteCrewFragment
import com.djekagoran.mymovieapp.ui.main.OnFragmentListInteraction
import com.djekagoran.mymovieapp.utill.Constants
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_favorite_crew.*
import java.util.ArrayList

class FavoriteCrewActivity : BaseActivity(), OnFragmentListInteraction {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_crew)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FavoriteCrewFragment.newInstance())
                .commit()
        }

        setActionBar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        app_bar.addOnOffsetChangedListener(object: AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsing_toolbar_layout.title = getString(R.string.title_favorite_actors)
                    isShow = true
                } else if(isShow) {
                    collapsing_toolbar_layout.title = " "
                    isShow = false
                }
            }
        })
    }

    override fun onListFragmentInteraction(movie: Movie, list_genre: ArrayList<Genre>) {
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra(Constants.MOVIE, movie)
        intent.putExtra(Constants.LIST_GENRE, list_genre)
        startActivity(intent)
    }
}
