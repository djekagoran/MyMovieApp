package com.djekagoran.mymovieapp.data.api.repo

import com.djekagoran.mymovieapp.data.model.Genre
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class GenreRepo {

    @SerializedName("genres")
    var genres: ArrayList<Genre>? = null
}
