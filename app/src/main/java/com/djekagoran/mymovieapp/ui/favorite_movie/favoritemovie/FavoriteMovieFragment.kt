package com.djekagoran.mymovieapp.ui.favorite_movie.favoritemovie

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.djekagoran.mymovieapp.R
import com.djekagoran.mymovieapp.base.BaseFragment
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.ui.favorite_movie.OnListFavoriteAction
import com.djekagoran.mymovieapp.ui.main.OnFragmentListInteraction
import kotlinx.android.synthetic.main.favorite_movie_fragment.*

import javax.inject.Inject

class FavoriteMovieFragment : BaseFragment() {

    @Inject lateinit var adapter: FavoriteMovieRecycleViewAdapter
    @Inject lateinit var viewModel: FavoriteMovieViewModel
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private var mListener: OnFragmentListInteraction? = null

    private lateinit var firstMovie: Movie

    companion object {
        fun newInstance() = FavoriteMovieFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnFragmentListInteraction) {
            mListener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentListInteraction")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_movie_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoriteMovieViewModel::class.java)

        viewModel.getFavoriteMovies()

        viewModel.dataMovie!!.observe(this, renderMovies)

        viewModel.dataGenre.observe(this, renderMoviesGenre)

        viewModel.errorMsg.observe(this, Observer { Toast.makeText(context, it, Toast.LENGTH_LONG).show() })

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

    }

    private val renderMovies = Observer<List<Movie>> {
        adapter.setDataMovie(it)
        if (it.isNotEmpty()) {
            firstMovie = it[0]
        }

        adapter.setOnListInteraction(object : OnFragmentListInteraction {
            override fun onListFragmentInteraction(movie: Movie, list_genre: ArrayList<Genre>) {
                mListener!!.onListFragmentInteraction(movie, list_genre)
            }
        })
        adapter.setOnListFavoriteAction(object : OnListFavoriteAction {
            override fun onListFavoriteAction(movie: Movie, isChecked: Boolean) {
                viewModel.favorite(movie, isChecked)
            }
        })

        if (it.isEmpty()) msg.text = getString(R.string.no_favorites) else msg.text = null
    }

    private val renderMoviesGenre = Observer<ArrayList<Genre>> {
        adapter.setDataGenre(it)
        if (::firstMovie.isInitialized) {
            mListener!!.onListLoadDisplayFirstMovie(firstMovie, it)
        }
    }

}
