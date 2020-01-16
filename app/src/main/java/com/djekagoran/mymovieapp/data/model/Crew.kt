package com.djekagoran.mymovieapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "actor_table", primaryKeys = ["id"])
@Parcelize
data class Crew (
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("cast_id")
    var cast_id: Int = 0,

    @SerializedName("character")
    var character: String? = null,

    @SerializedName("credit_id")
    var credit_id: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("profile_path")
    var profile_path: String? = null,

    @SerializedName("create_date")
    var create_time: Long = 0
): Parcelable