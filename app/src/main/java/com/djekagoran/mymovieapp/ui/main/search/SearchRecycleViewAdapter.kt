package com.djekagoran.mymovieapp.ui.main.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.djekagoran.mymovieapp.R
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.ui.main.OnFragmentListInteraction
import com.djekagoran.mymovieapp.utill.Constants
import com.djekagoran.mymovieapp.utill.Constants.VIEW_TYPE_ITEM
import com.djekagoran.mymovieapp.utill.Constants.VIEW_TYPE_LOADING
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_search.view.*

import java.util.ArrayList

class SearchRecycleViewAdapter(private val picasso: Picasso) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListener: OnFragmentListInteraction? = null

    private var listMovie: ArrayList<Movie?>
    private var listGenre: ArrayList<Genre>

    init {
        listMovie = ArrayList()
        listGenre = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie_search, parent, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder.itemViewType == VIEW_TYPE_ITEM) {

            holder as ItemViewHolder
            holder.movie = listMovie[position]

            holder.title!!.text = holder.movie!!.title
            holder.rating!!.text = holder.movie!!.vote_average.toString()

            picasso.load(Constants.IMAGE_BASE_URL + Constants.IMAGE_W154 + holder.movie!!.poster_path)
                .into(holder.image)

            holder.view.setOnClickListener {
                mListener?.onListFragmentInteraction(holder.movie!!, listGenre)
            }
        }
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (listMovie[position] != null) {
            VIEW_TYPE_ITEM
        } else {
            VIEW_TYPE_LOADING
        }
    }

    class ItemViewHolder(var view: View) : RecyclerView.ViewHolder(view){

        internal var image = view.image
        internal var title = view.title
        internal var rating = view.rating

        var movie: Movie? = null
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    internal fun setDataMovie(data: ArrayList<Movie>) {
        this.listMovie = ArrayList()
        this.listMovie.addAll(data)
        notifyDataSetChanged()
    }

    internal fun setDataGenre(data: ArrayList<Genre>) {
        this.listGenre = data
        notifyDataSetChanged()
    }

    internal fun addNullData() {
        listMovie.add(null)
        notifyItemInserted(listMovie.size - 1)
    }

    internal fun removeNull() {
        listMovie.removeAt(listMovie.size - 1)
        notifyItemRemoved(listMovie.size)
    }

    internal fun setOnListInteraction(mListener: OnFragmentListInteraction?) {
        this.mListener = mListener
    }

}
