package com.djekagoran.mymovieapp.ui.recently_movie.recentlymovie

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.djekagoran.mymovieapp.R
import com.djekagoran.mymovieapp.base.BaseFragment
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.ui.detail_movie.DetailMovieActivity
import com.djekagoran.mymovieapp.ui.main.OnFragmentListInteraction
import com.djekagoran.mymovieapp.utill.Constants
import kotlinx.android.synthetic.main.recently_movies_fragment.*
import javax.inject.Inject

class RecentlyMovieFragment : BaseFragment() {

    @Inject lateinit var adapter: RecentlyMovieRecycleViewAdapter
    @Inject lateinit var viewModel: RecentlyMovieViewModel
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance() = RecentlyMovieFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.recently_movies_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RecentlyMovieViewModel::class.java)

        viewModel.getRecentlyMovies()!!.observe(this, renderMovies)

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    private val renderMovies = Observer<List<Movie>> {

        adapter.setDataMovie(it)

        adapter.setOnListInteraction(object : OnFragmentListInteraction {
            override fun onListFragmentInteraction(movie: Movie, list_genre: java.util.ArrayList<Genre>) {
                val intent = Intent(context, DetailMovieActivity::class.java)
                intent.putExtra(Constants.MOVIE, movie)
                intent.putExtra(Constants.LIST_GENRE, list_genre)
                startActivity(intent)
            }
        })

        if (it.isEmpty()) msg.text = getString(R.string.no_recently) else msg.text = null
    }

}
