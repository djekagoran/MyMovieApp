package com.djekagoran.mymovieapp.ui.detail_movie

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.djekagoran.mymovieapp.R
import com.djekagoran.mymovieapp.base.BaseActivity
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.ui.detail_movie.detailmovie.DetailMovieFragment
import com.djekagoran.mymovieapp.ui.main.OnFragmentListInteraction
import com.djekagoran.mymovieapp.utill.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_movie.*
import java.util.ArrayList
import javax.inject.Inject

class DetailMovieActivity: BaseActivity(), OnFragmentListInteraction {

    @Inject
    lateinit var picasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        val movie: Movie = intent.getParcelableExtra(Constants.MOVIE)!!
        val listGenre: ArrayList<Genre> = intent.getParcelableArrayListExtra<Genre>(Constants.LIST_GENRE)!!

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailMovieFragment.newInstance(movie, listGenre))
                .commit()
        }

        setActionBar(movie.backdrop_path)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setActionBar(backdrop_link: String?) {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        picasso.load(Constants.IMAGE_BASE_URL + Constants.IMAGE_W500 + backdrop_link)
            .into(backdrop)
    }

    override fun onListFragmentInteraction(movie: Movie, list_genre: ArrayList<Genre>) {
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra(Constants.MOVIE, movie)
        intent.putExtra(Constants.LIST_GENRE, list_genre)
        startActivity(intent)
    }

}
