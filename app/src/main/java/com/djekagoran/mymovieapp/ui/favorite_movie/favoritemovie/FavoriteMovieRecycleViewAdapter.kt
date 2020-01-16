package com.djekagoran.mymovieapp.ui.favorite_movie.favoritemovie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.djekagoran.mymovieapp.R
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.ui.favorite_movie.OnListFavoriteAction
import com.djekagoran.mymovieapp.ui.main.OnFragmentListInteraction
import com.djekagoran.mymovieapp.utill.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_favorite.view.*

import java.util.ArrayList

class FavoriteMovieRecycleViewAdapter(private val picasso: Picasso) :
    RecyclerView.Adapter<FavoriteMovieRecycleViewAdapter.ViewHolder>() {

    private var mListener: OnFragmentListInteraction? = null
    private var favListener: OnListFavoriteAction? = null

    private var listMovie: ArrayList<Movie>
    private var listGenre: ArrayList<Genre>

    init {
        listMovie = ArrayList()
        listGenre = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_favorite, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.title!!.text = listMovie[position].title

        picasso.load(Constants.IMAGE_BASE_URL + Constants.IMAGE_W154 + listMovie[position].poster_path)
            .into(holder.image)

        holder.view.setOnClickListener {
            mListener?.onListFragmentInteraction(listMovie[position], listGenre)
        }
        holder.toggleFavorite.isChecked = true

        holder.toggleFavorite.setOnCheckedChangeListener { _, isChecked ->
            favListener!!.onListFavoriteAction(listMovie[position], isChecked)
        }
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        internal var image = view.image
        internal var title = view.title
        internal var toggleFavorite = view.toggle_favorite
    }

    internal fun setDataMovie(data: List<Movie>) {
        this.listMovie = ArrayList(data)
        notifyDataSetChanged()
    }

    internal fun setDataGenre(data: ArrayList<Genre>) {
        this.listGenre = data
        notifyDataSetChanged()
    }

    internal fun setOnListInteraction(mListener: OnFragmentListInteraction?) {
        this.mListener = mListener
    }

    internal fun setOnListFavoriteAction(favListener: OnListFavoriteAction?) {
        this.favListener = favListener
    }

}
