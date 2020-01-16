package com.djekagoran.mymovieapp.ui.main.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djekagoran.mymovieapp.data.DataManager
import com.djekagoran.mymovieapp.data.api.repo.GenreRepo
import com.djekagoran.mymovieapp.data.api.repo.PopularRepo
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.utill.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class PopularViewModel @Inject constructor(private val appDataManager: DataManager): ViewModel() {

    private var page = 1

    private val _dataMovie = MutableLiveData<ArrayList<Movie>>()
    val dataMovie: LiveData<ArrayList<Movie>> = _dataMovie

    private val _dataGenre = MutableLiveData<ArrayList<Genre>>()
    val dataGenre: LiveData<ArrayList<Genre>> = _dataGenre

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> = _toastMsg

    private val _loadingMore = MutableLiveData<Boolean>()
    val loadingMore: LiveData<Boolean> = _loadingMore

    fun getPopularMovies() {

        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = appDataManager.loadMoviePopular(Constants.API_KEY, page)
                withContext(Dispatchers.Main) {
                    onMoviesLoad(data)
                }
            } catch (e: Exception) {
                onErrorMoviesLoad(e)
            }
        }
    }

    private fun onMoviesLoad(repo: PopularRepo) {

        _loading.value = false
        _dataMovie.value = repo.results

        loadDataGenres()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                appDataManager.savePopular(repo.results!!)
            } catch (e: Exception) {
                _toastMsg.postValue(e.message)
            }
        }
    }

    private suspend fun onErrorMoviesLoad(throwable: Throwable) {

        _loading.postValue(false)

        appDataManager.popular.collect {
            if (it.isNotEmpty()) {
                _dataMovie.postValue(ArrayList(it))
                loadDataGenres()
            } else {
                _toastMsg.postValue(throwable.message)
            }
        }
    }

    fun loadDataMore() {

        _loadingMore.value = true
        page++

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = appDataManager.loadMoviePopular(Constants.API_KEY, page)
                withContext(Dispatchers.Main) {
                    onMoviesLoadMore(data)
                }
            } catch (e: Exception) {
                onErrorMoviesLoadMore(e)
            }
        }
    }

    private fun onMoviesLoadMore(repo: PopularRepo) {

        _loadingMore.value = false
        if (repo.results!!.isNotEmpty()) {
            _dataMovie.value!!.addAll(repo.results!!)
            _dataMovie.value = _dataMovie.value
        } else {
            _toastMsg.value = "No more data"
        }
    }

    private fun onErrorMoviesLoadMore(throwable: Throwable) {
        page--
        _loading.postValue(false)
        _toastMsg.postValue(throwable.message)
    }

    private fun loadDataGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = appDataManager.loadMovieGenres(Constants.API_KEY)
                withContext(Dispatchers.Main) {
                    onGenresLoad(data)
                }
            } catch (e: Exception) {
                onErrorGenresLoad(e)
            }
        }
    }

    private fun onGenresLoad(genreRepo: GenreRepo) {

        _dataGenre.value = genreRepo.genres

        viewModelScope.launch(Dispatchers.IO) {
            try {
                appDataManager.saveGenre(genreRepo.genres!!.toList())
            } catch (e: Exception) {
                _toastMsg.postValue(e.message)
            }
        }
    }

    private suspend fun onErrorGenresLoad(throwable: Throwable) {
        appDataManager.genres.collect {
            if (it.isNotEmpty()) {
                _dataGenre.postValue(ArrayList(it))
            } else {
                _toastMsg.postValue(throwable.message)
            }
        }
    }

}
