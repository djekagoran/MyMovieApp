package com.djekagoran.mymovieapp.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.util.ArrayList

class MovieGenreIdConverter {

    @TypeConverter
    fun toType(movie_type: String): ArrayList<Int>? {
        val gson = Gson()
        return gson.fromJson<ArrayList<Int>>(movie_type, object : TypeToken<ArrayList<Int>>() { }.type)
    }

    @TypeConverter
    fun toString(list: ArrayList<Int>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

}

