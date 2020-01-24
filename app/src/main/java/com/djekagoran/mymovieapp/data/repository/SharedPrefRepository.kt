package com.djekagoran.mymovieapp.data.repository

import android.content.SharedPreferences
import com.djekagoran.mymovieapp.utill.Constants
import javax.inject.Inject

class SharedPrefRepository @Inject constructor(private val sharedPref: SharedPreferences) : SharedPrefView {

    override fun saveIsNight(value: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean(Constants.IS_NIGHT, value)
        editor.apply()
    }

    override fun loadIsNight() = sharedPref.getBoolean(Constants.IS_NIGHT, false)

}
