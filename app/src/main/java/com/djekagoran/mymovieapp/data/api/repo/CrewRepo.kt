package com.djekagoran.mymovieapp.data.api.repo

import com.djekagoran.mymovieapp.data.model.Crew
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class CrewRepo {

    @SerializedName("cast")
    var crew: ArrayList<Crew>? = null
}
