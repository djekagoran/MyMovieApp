package com.djekagoran.mymovieapp.ui.detail_movie.detailmovie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.djekagoran.mymovieapp.R
import com.djekagoran.mymovieapp.base.BaseFragment
import com.djekagoran.mymovieapp.data.model.Crew
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.ui.detail_crew.DetailCrewActivity
import com.djekagoran.mymovieapp.ui.detail_movie.OnListCrewInteraction
import com.djekagoran.mymovieapp.ui.main.OnFragmentListInteraction
import com.djekagoran.mymovieapp.utill.Constants
import com.djekagoran.mymovieapp.utill.Utills
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_movie_fragment.*
import javax.inject.Inject

class DetailMovieFragment : BaseFragment() {

    @Inject lateinit var adapterCrew: CrewRecycleViewAdapter
    @Inject lateinit var adapterRecomm: RecommRecycleViewAdapter
    @Inject lateinit var viewModel: DetailMovieViewModel
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var picasso: Picasso

    private var mListener: OnFragmentListInteraction? = null

    lateinit var listGenre: ArrayList<Genre>

    companion object {
        fun newInstance(movie: Movie, list_genre: ArrayList<Genre>): DetailMovieFragment {
            val fragment = DetailMovieFragment()
            val bundle = Bundle()
            bundle.putParcelable(Constants.MOVIE, movie)
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
    ): View? {
        return inflater.inflate(R.layout.detail_movie_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailMovieViewModel::class.java)

        val movie: Movie = arguments!!.getParcelable(Constants.MOVIE)!!
        listGenre = arguments!!.getParcelableArrayList<Genre>(Constants.LIST_GENRE)!!

        displayMovie(movie, listGenre)

        viewModel.isFavorite(movie)

        viewModel.isFavorite.observe(this, renderFavorite)

        viewModel.loadCrew(movie.id)

        viewModel.dataCrew.observe(this, renderCrew)

        viewModel.dataRecomm.observe(this, renderRecomm)

        viewModel.loadingCrew.observe(this, Observer { progress_crew.visibility = if (it) View.VISIBLE else View.GONE })

        viewModel.loadingRecomm.observe(this, Observer { progress_recommendation.visibility = if (it) View.VISIBLE else View.GONE })

        viewModel.errorCrew.observe(this, Observer { container_crew.visibility = View.GONE })

        viewModel.errorRecomm.observe(this, Observer { container_recommendation.visibility = View.GONE })

        viewModel.toastMsg.observe(this, Observer { Toast.makeText(context, it, Toast.LENGTH_LONG).show() })

        toggle_favorite.setOnClickListener {
            viewModel.favorite(movie, toggle_favorite.isChecked)
        }

        recycler_view_crew.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        recycler_view_crew.adapter = adapterCrew

        recycler_view_recommendation.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        recycler_view_recommendation.adapter = adapterRecomm
    }

    private fun displayMovie(movie: Movie, listGenre: ArrayList<Genre>) {

        picasso.load(Constants.IMAGE_BASE_URL + Constants.IMAGE_W500 + movie.poster_path)
            .into(poster)

        title.text = movie.title
        genre.text = Utills.getGenresToString(Utills.getGenres(listGenre, movie.genre_ids!!))

        rating.text = movie.vote_average.toString()
        description.text = movie.overview
        release_date.text = movie.release_date
    }

    private val renderFavorite = Observer<Boolean> {
        if (toggle_favorite.isChecked != it) {
            toggle_favorite.isChecked = it
        }
    }

    private val renderCrew = Observer<ArrayList<Crew>> {
        adapterCrew.setDataCrew(it)

        adapterCrew.setOnListCrewInteraction(object : OnListCrewInteraction {
            override fun onListCrewInteraction(crew: Crew) {
                val intent = Intent(context, DetailCrewActivity::class.java)
                intent.putExtra(Constants.CREW, crew)
                intent.putExtra(Constants.LIST_GENRE, listGenre)
                startActivity(intent)
            }
        })
    }

    private val renderRecomm = Observer<ArrayList<Movie>> {

        adapterRecomm.setDataMovie(it)
        adapterRecomm.setDataGenre(listGenre)

        adapterRecomm.setOnListInteraction(object : OnFragmentListInteraction{
            override fun onListFragmentInteraction(movie: Movie, list_genre: java.util.ArrayList<Genre>) {
                mListener!!.onListFragmentInteraction(movie, list_genre)
            }
        })
    }

    override fun onDestroyView() {
        recycler_view_crew.adapter = null
        recycler_view_recommendation.adapter = null
        super.onDestroyView()
    }
}
