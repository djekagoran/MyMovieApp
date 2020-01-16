package com.djekagoran.mymovieapp.ui.main.top_rated

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.djekagoran.mymovieapp.R
import com.djekagoran.mymovieapp.base.BaseFragment
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.ui.main.OnFragmentListInteraction
import com.djekagoran.mymovieapp.utill.InfiniteScrollListener
import kotlinx.android.synthetic.main.top_rated_fragment.*

import javax.inject.Inject

class TopRatedFragment : BaseFragment() {

    @Inject lateinit var adapter: TopRatedRecycleViewAdapter
    @Inject lateinit var viewModel: TopRatedViewModel
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private var mListener: OnFragmentListInteraction? = null

    private var infiniteScrollListener: InfiniteScrollListener? = null

    private lateinit var firstMovie: Movie

    companion object {
        fun newInstance() = TopRatedFragment()
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
        return inflater.inflate(R.layout.top_rated_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TopRatedViewModel::class.java)

        viewModel.getTopRatedMovies()

        viewModel.dataMovie.observe(this, renderMovies)

        viewModel.dataGenre.observe(this, renderMoviesGenre)

        viewModel.loading.observe(this, Observer { progressBar.visibility = if (it) VISIBLE else GONE })

        viewModel.toastMsg.observe(this, Observer { Toast.makeText(context, it, Toast.LENGTH_LONG).show() })

        viewModel.loadingMore.observe(this, Observer {
            if (it) {
                adapter.addNullData()
            } else {
                infiniteScrollListener!!.setLoaded()
                adapter.removeNull()
            }
        })

        val layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.grid_movie_column))
        infiniteScrollListener = InfiniteScrollListener(layoutManager, scrollListener)
        infiniteScrollListener!!.setLoaded()

        recyclerView.layoutManager = layoutManager
        recyclerView.addOnScrollListener(infiniteScrollListener!!)
        recyclerView.adapter = adapter

    }

    private var scrollListener =  object : InfiniteScrollListener.OnLoadMoreListener {
        override fun onLoadMore() {
            viewModel.loadDataMore()
        }
    }

    private val renderMovies = Observer<ArrayList<Movie>> {
        adapter.setDataMovie(it)
        if (it.isNotEmpty()) {
            firstMovie = it[0]
        }

        adapter.setOnListInteraction(object : OnFragmentListInteraction {
            override fun onListFragmentInteraction(movie: Movie, list_genre: ArrayList<Genre>) {
                mListener!!.onListFragmentInteraction(movie, list_genre)
            }
        })
    }

    private val renderMoviesGenre = Observer<ArrayList<Genre>> {
        adapter.setDataGenre(it)
        if (::firstMovie.isInitialized) {
            mListener!!.onListLoadDisplayFirstMovie(firstMovie, it)
        }
    }

    override fun onDestroyView() {
        recyclerView.adapter = null
        super.onDestroyView()
    }

}
