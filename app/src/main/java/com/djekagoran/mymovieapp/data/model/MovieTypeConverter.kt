package com.djekagoran.mymovieapp.data.model

import androidx.room.TypeConverter

class MovieTypeConverter {

    @TypeConverter
    fun toType(movie_type: String): MovieType {
        return MovieType.valueOf(movie_type)
    }

    @TypeConverter
    fun toString(movieType: MovieType): String {
        return movieType.name
    }

}

