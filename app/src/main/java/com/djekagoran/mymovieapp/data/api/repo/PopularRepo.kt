package com.djekagoran.mymovieapp.data.api.repo

import com.djekagoran.mymovieapp.data.model.Movie
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class PopularRepo {

    @SerializedName("page")
    var page: Int = 0

    @SerializedName("results")
    var results: ArrayList<Movie>? = null
}
