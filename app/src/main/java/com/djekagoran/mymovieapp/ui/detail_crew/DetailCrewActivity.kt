package com.djekagoran.mymovieapp.ui.detail_crew

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.djekagoran.mymovieapp.R
import com.djekagoran.mymovieapp.base.BaseActivity
import com.djekagoran.mymovieapp.data.model.Crew
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.ui.detail_crew.detailcrew.DetailCrewFragment
import com.djekagoran.mymovieapp.ui.detail_crew.detailcrew.DetailCrewViewModel
import com.djekagoran.mymovieapp.ui.detail_movie.DetailMovieActivity
import com.djekagoran.mymovieapp.ui.main.OnFragmentListInteraction
import com.djekagoran.mymovieapp.utill.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_crew.*
import kotlinx.android.synthetic.main.activity_detail_movie.backdrop
import kotlinx.android.synthetic.main.activity_detail_movie.collapsing_toolbar_layout
import kotlinx.android.synthetic.main.activity_detail_movie.toolbar
import java.util.ArrayList
import javax.inject.Inject

class DetailCrewActivity : BaseActivity(), OnFragmentListInteraction {

    @Inject
    lateinit var picasso: Picasso
    @Inject
    lateinit var viewModel: DetailCrewViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_crew)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailCrewViewModel::class.java)

        val crew: Crew = intent.getParcelableExtra(Constants.CREW)!!
        val listGenre: ArrayList<Genre> = intent.getParcelableArrayListExtra<Genre>(Constants.LIST_GENRE)!!

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailCrewFragment.newInstance(crew, listGenre))
                .commitNow()
        }

        setActionBar(crew)

        viewModel.isFavorite(crew).observe(this, renderFavorite)

        fab.setOnClickListener {
            viewModel.favorite(crew)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setActionBar(crew: Crew) {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        collapsing_toolbar_layout.title = crew.name
        picasso.load(Constants.IMAGE_BASE_URL + Constants.IMAGE_W500 + crew.profile_path)
            .into(backdrop)
    }

    private val renderFavorite = Observer<Boolean> {
        fab.setImageDrawable(ContextCompat.getDrawable(this, if (it) R.drawable.heart_red else R.drawable.heart))
    }

    override fun onListFragmentInteraction(movie: Movie, list_genre: ArrayList<Genre>) {
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra(Constants.MOVIE, movie)
        intent.putExtra(Constants.LIST_GENRE, list_genre)
        startActivity(intent)
    }

}
