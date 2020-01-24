package com.djekagoran.mymovieapp.data.repository

interface SharedPrefView {

    fun saveIsNight(value: Boolean)
    fun loadIsNight(): Boolean

}
