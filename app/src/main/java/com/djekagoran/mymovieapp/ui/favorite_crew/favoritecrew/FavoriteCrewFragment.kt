package com.djekagoran.mymovieapp.ui.favorite_crew.favoritecrew

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.djekagoran.mymovieapp.R
import com.djekagoran.mymovieapp.base.BaseFragment
import com.djekagoran.mymovieapp.data.model.Crew
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.ui.detail_crew.DetailCrewActivity
import com.djekagoran.mymovieapp.ui.detail_movie.OnListCrewInteraction
import com.djekagoran.mymovieapp.utill.Constants
import kotlinx.android.synthetic.main.favorite_crew_fragment.*
import javax.inject.Inject

class FavoriteCrewFragment : BaseFragment() {

    @Inject lateinit var adapter: FavoriteCrewRecycleViewAdapter
    @Inject lateinit var viewModel: FavoriteCrewViewModel
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance() = FavoriteCrewFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.favorite_crew_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoriteCrewViewModel::class.java)

        viewModel.getActors()!!.observe(this, renderActors)

        recyclerView.layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.grid_actor_column))
        recyclerView.adapter = adapter

    }

    private val renderActors = Observer<List<Crew>> {
        adapter.setDataMovie(it)

        adapter.setOnListCrewInteraction(object : OnListCrewInteraction {
            override fun onListCrewInteraction(crew: Crew) {
                val intent = Intent(context, DetailCrewActivity::class.java)
                intent.putExtra(Constants.CREW, crew)
                intent.putExtra(Constants.LIST_GENRE, ArrayList<Genre>())
                startActivity(intent)
            }
        })

        if (it.isEmpty()) msg.text = getString(R.string.no_actors) else msg.text = null
    }

}
