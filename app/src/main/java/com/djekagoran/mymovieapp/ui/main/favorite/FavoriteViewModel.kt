package com.djekagoran.mymovieapp.ui.main.favorite

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.djekagoran.mymovieapp.data.repository.SharedPrefView
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val sharedPref: SharedPrefView): ViewModel() {

    private val _isNight = MutableLiveData<Boolean>()
    val isNight: LiveData<Boolean> = _isNight

    fun onThemeCheckedChange(b: Boolean) {
        if (b) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        sharedPref.saveIsNight(b)
    }

    fun isNight() {
        _isNight.value = sharedPref.loadIsNight()
    }
}
