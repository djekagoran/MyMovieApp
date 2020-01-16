package com.djekagoran.mymovieapp.data.api.repo

import com.djekagoran.mymovieapp.data.model.Movie
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class SearchRepo {

    @SerializedName("page")
    var page: Int = 0

    @SerializedName("total_results")
    var total_results: Int = 0

    @SerializedName("total_pages")
    var total_pages: Int = 0

    @SerializedName("results")
    var results: ArrayList<Movie>? = null
}
