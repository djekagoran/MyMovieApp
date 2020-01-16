package com.djekagoran.mymovieapp.ui.detail_movie.detailmovie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.djekagoran.mymovieapp.R
import com.djekagoran.mymovieapp.data.model.Crew
import com.djekagoran.mymovieapp.ui.detail_movie.OnListCrewInteraction
import com.djekagoran.mymovieapp.utill.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_crew.view.*

import java.util.ArrayList

class CrewRecycleViewAdapter(private val picasso: Picasso) :
    RecyclerView.Adapter<CrewRecycleViewAdapter.ViewHolder>() {

    private var mListener: OnListCrewInteraction? = null

    private var listCrew: ArrayList<Crew>

    init {
        listCrew = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_crew, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.title!!.text = listCrew[position].name

        picasso.load(Constants.IMAGE_BASE_URL + Constants.IMAGE_W154 + listCrew[position].profile_path)
            .into(holder.image)

        holder.view.setOnClickListener {
            mListener?.onListCrewInteraction(listCrew[position])
        }
    }

    override fun getItemCount(): Int {
        return listCrew.size
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        internal var image = view.image
        internal var title = view.title
    }

    internal fun setDataCrew(data: ArrayList<Crew>) {
        this.listCrew = data
        notifyDataSetChanged()
    }

    internal fun setOnListCrewInteraction(mListener: OnListCrewInteraction?) {
        this.mListener = mListener
    }

}
