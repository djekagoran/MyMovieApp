package com.djekagoran.mymovieapp.ui.detail_crew.detailcrew

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djekagoran.mymovieapp.data.DataManager
import com.djekagoran.mymovieapp.data.model.Crew
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.utill.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class DetailCrewViewModel @Inject constructor(private val appDataManager: DataManager): ViewModel() {

    private val _dataMovie = MutableLiveData<ArrayList<Movie>>()
    val dataMovie: LiveData<ArrayList<Movie>> = _dataMovie

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> = _toastMsg

    private val _isFavorite = MutableLiveData<Boolean>()
    private val isFavorite: LiveData<Boolean> = _isFavorite

    fun isFavorite(crew: Crew): LiveData<Boolean> {
        viewModelScope.launch {
            appDataManager.countFavoriteCrew(crew).collect{ _isFavorite.value = (it > 0) }
        }
        return isFavorite
    }

    fun favorite(crew: Crew) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (_isFavorite.value!!) appDataManager.deleteFavoriteCrew(crew) else appDataManager.saveFavoriteCrew(crew)
                _isFavorite.postValue(!(_isFavorite.value)!!)
            } catch (e: Exception) {
                _toastMsg.postValue(e.message)
            }
        }
    }

    fun getActorMovies(crew: Crew) {

        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = appDataManager.loadActorMovie(crew.id, Constants.API_KEY)
                withContext(Dispatchers.Main) {
                    _loading.value = false
                    _dataMovie.value = data.cast
                }
            } catch (e: Exception) {
                _loading.postValue(false)
                _toastMsg.postValue(e.message)
            }
        }
    }
}
