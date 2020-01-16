package com.djekagoran.mymovieapp.ui.favorite_crew.favoritecrew

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.djekagoran.mymovieapp.R
import com.djekagoran.mymovieapp.data.model.Crew
import com.djekagoran.mymovieapp.ui.detail_movie.OnListCrewInteraction
import com.djekagoran.mymovieapp.utill.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_crew_grid.view.*

import java.util.ArrayList

class FavoriteCrewRecycleViewAdapter(private val picasso: Picasso) :
    RecyclerView.Adapter<FavoriteCrewRecycleViewAdapter.ViewHolder>() {

    private var favListener: OnListCrewInteraction? = null

    private var listCrew: ArrayList<Crew>

    init {
        listCrew = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_crew_grid, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.title!!.text = listCrew[position].name

        picasso.load(Constants.IMAGE_BASE_URL + Constants.IMAGE_W154 + listCrew[position].profile_path)
            .into(holder.image)

        holder.view.setOnClickListener {
            favListener?.onListCrewInteraction(listCrew[position])
        }
    }

    override fun getItemCount(): Int {
        return listCrew.size
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        internal var image = view.image
        internal var title = view.title
    }

    internal fun setDataMovie(data: List<Crew>) {
        this.listCrew = ArrayList(data)
        notifyDataSetChanged()
    }

    internal fun setOnListCrewInteraction(favListener: OnListCrewInteraction?) {
        this.favListener = favListener
    }

}
