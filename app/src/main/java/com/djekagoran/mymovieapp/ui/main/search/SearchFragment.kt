package com.djekagoran.mymovieapp.ui.main.search

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
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.djekagoran.mymovieapp.R
import com.djekagoran.mymovieapp.base.BaseFragment
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.ui.main.OnFragmentListInteraction
import com.djekagoran.mymovieapp.utill.InfiniteScrollListener
import kotlinx.android.synthetic.main.search_fragment.*

import javax.inject.Inject

class SearchFragment : BaseFragment() {

    @Inject lateinit var adapter: SearchRecycleViewAdapter
    @Inject lateinit var viewModel: SearchViewModel
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private var mListener: OnFragmentListInteraction? = null

    private var infiniteScrollListener: InfiniteScrollListener? = null

    private lateinit var firstMovie: Movie

    companion object {
        fun newInstance() = SearchFragment()
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
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)


        viewModel.getSearchMovies(search_view)

        viewModel.dataMovie.observe(this, renderMovies)

        viewModel.dataGenre.observe(this, renderMoviesGenre)

        viewModel.loading.observe(this, Observer { progressBar.visibility = if (it) View.VISIBLE else View.GONE })

        viewModel.toastMsg.observe(this, Observer { Toast.makeText(context, it, Toast.LENGTH_LONG).show() })

        viewModel.loadingMore.observe(this, Observer {
            if (it) {
                adapter.addNullData()
            } else {
                infiniteScrollListener!!.setLoaded()
                adapter.removeNull()
            }
        })

        val linearLayoutManager = LinearLayoutManager(context, VERTICAL, false)
        infiniteScrollListener = InfiniteScrollListener(linearLayoutManager, scrollListener)
        infiniteScrollListener!!.setLoaded()

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addOnScrollListener(infiniteScrollListener!!)
        recyclerView.adapter = adapter

        search_view.onActionViewExpanded()

    }

    private var scrollListener =  object : InfiniteScrollListener.OnLoadMoreListener {
        override fun onLoadMore() {
            if (search_view.query.toString().isNotEmpty()) {
                viewModel.searchLoadMore(search_view.query.toString())
            }
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
