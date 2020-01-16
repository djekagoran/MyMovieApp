package com.djekagoran.mymovieapp.ui.detail_crew.detailcrew

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.djekagoran.mymovieapp.R
import com.djekagoran.mymovieapp.base.BaseFragment
import com.djekagoran.mymovieapp.data.model.Crew
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.ui.main.OnFragmentListInteraction
import com.djekagoran.mymovieapp.utill.Constants
import kotlinx.android.synthetic.main.detail_crew_fragment.*
import javax.inject.Inject

class DetailCrewFragment : BaseFragment() {

    @Inject lateinit var adapter: DetailCrewRecycleViewAdapter
    @Inject lateinit var viewModel: DetailCrewViewModel
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private var mListener: OnFragmentListInteraction? = null

    private lateinit var listGenre: ArrayList<Genre>

    companion object {
        fun newInstance(crew: Crew, list_genre: ArrayList<Genre>): DetailCrewFragment {
            val fragment = DetailCrewFragment()
            val bundle = Bundle()
            bundle.putParcelable(Constants.CREW, crew)
            bundle.putParcelableArrayList(Constants.LIST_GENRE, list_genre)
            fragment.arguments = bundle
            return fragment
        }
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
    ): View {
        return inflater.inflate(R.layout.detail_crew_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailCrewViewModel::class.java)

        val crew: Crew = arguments!!.getParcelable(Constants.CREW)!!
        listGenre = arguments!!.getParcelableArrayList<Genre>(Constants.LIST_GENRE)!!

        viewModel.getActorMovies(crew)

        viewModel.dataMovie.observe(this, renderMovies)

        viewModel.loading.observe(this, Observer { progressBar.visibility = if (it) View.VISIBLE else View.GONE })

        viewModel.toastMsg.observe(this, Observer { Toast.makeText(context, it, Toast.LENGTH_LONG).show() })

        recyclerView.layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.grid_actor_movie_column))
        recyclerView.adapter = adapter

    }

    private val renderMovies = Observer<ArrayList<Movie>> {
        adapter.setDataMovie(it)

        adapter.setOnListInteraction(object : OnFragmentListInteraction {
            override fun onListFragmentInteraction(movie: Movie, list_genre: ArrayList<Genre>) {
                mListener!!.onListFragmentInteraction(movie, list_genre)
            }
        })
    }

}
