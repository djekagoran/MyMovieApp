package com.djekagoran.mymovieapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "genre_table")
@Parcelize
data class Genre(
    @PrimaryKey
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("name")
    var title: String? = null
) : Parcelable