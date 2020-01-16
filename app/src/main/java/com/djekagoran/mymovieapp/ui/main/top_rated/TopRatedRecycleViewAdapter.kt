package com.djekagoran.mymovieapp.ui.main.top_rated

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

class TopRatedRecycleViewAdapter(private val picasso: Picasso) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListener: OnFragmentListInteraction? = null

    private var listMovie: ArrayList<Movie?>
    private var listGenre: ArrayList<Genre>

    init {
        listMovie = ArrayList()
        listGenre = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constants.VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder.itemViewType == Constants.VIEW_TYPE_ITEM) {

            holder as ItemViewHolder
            holder.movie = listMovie[position]

            holder.title!!.text = listMovie[position]!!.title
            holder.rating!!.text = listMovie[position]!!.vote_average.toString()

            val genreString = Utills.getGenresToString(Utills.getGenres(listGenre, listMovie[position]!!.genre_ids!!))

            holder.genre!!.text = genreString
            holder.genre!!.visibility = if (genreString.isEmpty()) View.GONE else View.VISIBLE

            picasso.load(Constants.IMAGE_BASE_URL + Constants.IMAGE_W154 + listMovie[position]!!.poster_path)
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
            Constants.VIEW_TYPE_ITEM
        } else {
            Constants.VIEW_TYPE_LOADING
        }
    }

    class ItemViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        internal var image = view.image
        internal var title = view.title
        internal var genre = view.genre
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
