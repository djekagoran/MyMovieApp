package com.djekagoran.mymovieapp.ui.detail_crew.detailcrew

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.djekagoran.mymovieapp.R
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.ui.main.OnFragmentListInteraction
import com.djekagoran.mymovieapp.utill.Constants
import com.djekagoran.mymovieapp.utill.Utills
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*

import java.util.ArrayList

class DetailCrewRecycleViewAdapter(private val picasso: Picasso) :
    RecyclerView.Adapter<DetailCrewRecycleViewAdapter.ViewHolder>() {

    private var mListener: OnFragmentListInteraction? = null

    private var listMovie: ArrayList<Movie>
    private var listGenre: ArrayList<Genre>

    init {
        listMovie = ArrayList()
        listGenre = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.title!!.text = listMovie[position].title
        holder.rating!!.text = listMovie[position].vote_average.toString()

        val genreString = Utills.getGenresToString(Utills.getGenres(listGenre, listMovie[position].genre_ids!!))

        holder.genre!!.text = genreString
        holder.genre!!.visibility = if (genreString.isEmpty()) View.GONE else View.VISIBLE

        picasso.load(Constants.IMAGE_BASE_URL + Constants.IMAGE_W154 + listMovie[position].poster_path)
            .into(holder.image)

        holder.view.setOnClickListener {
            mListener?.onListFragmentInteraction(listMovie[position], listGenre)
        }
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        internal var image = view.image
        internal var title = view.title
        internal var genre = view.genre
        internal var rating = view.rating
    }

    internal fun setDataMovie(data: ArrayList<Movie>) {
        this.listMovie = data
        notifyDataSetChanged()
    }

    internal fun setDataGenre(data: ArrayList<Genre>) {
        this.listGenre = data
        notifyDataSetChanged()
    }

    internal fun setOnListInteraction(mListener: OnFragmentListInteraction?) {
        this.mListener = mListener
    }

}
