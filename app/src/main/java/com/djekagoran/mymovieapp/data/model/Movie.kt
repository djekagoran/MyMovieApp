package com.djekagoran.mymovieapp.data.model

import android.os.Parcelable
import androidx.room.Entity 
import androidx.room.TypeConverters 
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize 
import kotlin.collections.ArrayList

@Entity(tableName = "movie_table", primaryKeys = ["movie_type", "id"])
@TypeConverters(MovieTypeConverter::class, MovieGenreIdConverter::class)
@Parcelize
data class Movie(
    
    @Transient
    var isFavorite: Boolean = false,
 
    @SerializedName("movie_type")
    var movie_type: MovieType = MovieType.POPULAR,

    @SerializedName("create_date")
    var create_time: Long = 0,

    @SerializedName("poster_path")
    var poster_path: String? = null,

    @SerializedName("overview")
    var overview: String? = null,

    @SerializedName("release_date")
    var release_date: String? = null,
 
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("backdrop_path")
    var backdrop_path: String? = null,

    @SerializedName("vote_average")
    var vote_average: Double? = null,

    @SerializedName("original_title")
    var original_title: String? = null,

    @SerializedName("genre_ids")
    var genre_ids: ArrayList<Int>? = null

): Parcelable