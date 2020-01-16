package com.djekagoran.mymovieapp.ui.detail_movie.detailmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djekagoran.mymovieapp.data.DataManager
import com.djekagoran.mymovieapp.data.api.repo.CrewRepo
import com.djekagoran.mymovieapp.data.api.repo.RecommendedRepo
import com.djekagoran.mymovieapp.data.model.Crew
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.utill.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class DetailMovieViewModel@Inject constructor(private val appDataManager: DataManager): ViewModel() {

    private val _dataCrew = MutableLiveData<ArrayList<Crew>>()
    val dataCrew: LiveData<ArrayList<Crew>> = _dataCrew

    private val _dataRecomm = MutableLiveData<ArrayList<Movie>>()
    val dataRecomm: LiveData<ArrayList<Movie>> = _dataRecomm

    private val _loadingCrew = MutableLiveData<Boolean>()
    val loadingCrew: LiveData<Boolean> = _loadingCrew

    private val _loadingRecomm = MutableLiveData<Boolean>()
    val loadingRecomm: LiveData<Boolean> = _loadingRecomm

    private val _errorCrew = MutableLiveData<String>()
    val errorCrew: LiveData<String> = _errorCrew

    private val _errorRecomm = MutableLiveData<String>()
    val errorRecomm: LiveData<String> = _errorRecomm

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> = _toastMsg


    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private var idMovie = 0

    fun loadCrew(id_movie: Int) {

        this.idMovie = id_movie

        _loadingCrew.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = appDataManager.loadMovieCrew(id_movie, Constants.API_KEY)
                withContext(Dispatchers.Main) {
                    onCrewLoaded(data)
                }
            } catch (e: Exception) {
                onErrorLoadCrew(e)
            }
        }
    }

    private fun onCrewLoaded(repo: CrewRepo) {
        _loadingCrew.value = false
        if (repo.crew!!.isNotEmpty()) {
            _dataCrew.value = repo.crew
        } else {
            _errorRecomm.value = "No crew found"
        }
        loadDataRecomm()
    }

    private fun onErrorLoadCrew(throwable: Throwable) {
        _errorCrew.postValue(throwable.message)
        loadDataRecomm()
    }

    private fun loadDataRecomm() {

        _loadingRecomm.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = appDataManager.loadMovieRecommended(idMovie, Constants.API_KEY)
                withContext(Dispatchers.Main) {
                    onRecommendationLoaded(data)
                }
            } catch (e: Exception) {
                onErrorLoadRecommendation(e)
            }
        }
    }

    private fun onRecommendationLoaded(repo: RecommendedRepo) {
        _loadingRecomm.value = false
        if (repo.results!!.isNotEmpty()) {
            _dataRecomm.value = repo.results
        } else {
            _errorRecomm.value = "No recommended found"
        }
    }

    private fun onErrorLoadRecommendation(throwable: Throwable) {
        _loadingRecomm.postValue(false)
        _errorRecomm.postValue(throwable.message)
    }

    fun isFavorite(movie: Movie) {

        viewModelScope.launch {
            appDataManager.countFavoriteMovie(movie).collect{ _isFavorite.value = (it > 0) }
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                appDataManager.saveRecently(movie)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun favorite(movie: Movie, isChecked: Boolean) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (isChecked) appDataManager.saveFavoriteMovie(movie) else appDataManager.deleteFavoriteMovie(movie)
                _isFavorite.postValue(!(_isFavorite.value)!!)
            } catch (e: Exception) {
                _toastMsg.postValue(e.message)
            }
        }
    }

}
