package com.djekagoran.mymovieapp.utill

import com.djekagoran.mymovieapp.data.model.Genre
import java.util.ArrayList

object Utills {

    fun getGenres(genres: List<Genre>, genre_ids: ArrayList<Int>): ArrayList<Genre> {
        val list = ArrayList<Genre>()

        if (genres.isNotEmpty()) {
            for (gm in genres) {
                if (genre_ids.contains(gm.id)) {
                    list.add(gm)
                }
            }
        }
        return list
    }

    fun getGenresToString(genres: ArrayList<Genre>): String {
        val str = StringBuilder()
        if (genres.isNotEmpty()) {
            for (gm in genres) {
                if (str.isNotEmpty()) {
                    str.append(", ")
                }
                str.append(gm.title)
            }
        }
        return str.toString()
    }

    fun getGenresToString(genres: List<Genre>): String {
        val str = StringBuilder()
        if (genres.isNotEmpty()) {
            for (gm in genres) {
                if (str.isNotEmpty()) {
                    str.append(", ")
                }
                str.append(gm.title)
            }
        }
        return str.toString()
    }

}
