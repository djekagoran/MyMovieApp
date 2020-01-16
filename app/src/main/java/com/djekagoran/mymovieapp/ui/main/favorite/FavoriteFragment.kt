package com.djekagoran.mymovieapp.ui.main.favorite

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.djekagoran.mymovieapp.R
import com.djekagoran.mymovieapp.base.BaseFragment
import com.djekagoran.mymovieapp.ui.favorite_crew.FavoriteCrewActivity
import com.djekagoran.mymovieapp.ui.favorite_movie.FavoriteMovieActivity
import com.djekagoran.mymovieapp.ui.recently_movie.RecentlyMovieActivity

import kotlinx.android.synthetic.main.favorite_fragment.*
import javax.inject.Inject

class FavoriteFragment : BaseFragment() {

    @Inject lateinit var viewModel: FavoriteViewModel
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoriteViewModel::class.java)

        card_favorite_movie.setOnClickListener {
            val intent = Intent(context, FavoriteMovieActivity::class.java)
            startActivity(intent)
        }
        card_favorite_actor.setOnClickListener {
            val intent = Intent(context, FavoriteCrewActivity::class.java)
            startActivity(intent)
        }
        card_recently_movie.setOnClickListener {
            val intent = Intent(context, RecentlyMovieActivity::class.java)
            startActivity(intent)
        }
    }
}
