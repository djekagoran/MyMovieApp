package com.djekagoran.mymovieapp.data.api.repo

import com.djekagoran.mymovieapp.data.model.Movie
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class ActorMovieRepo {

    @SerializedName("cast")
    var cast: ArrayList<Movie>? = null
}
