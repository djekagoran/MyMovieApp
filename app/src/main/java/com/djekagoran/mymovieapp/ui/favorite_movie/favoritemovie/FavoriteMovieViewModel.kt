package com.djekagoran.mymovieapp.ui.favorite_movie.favoritemovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djekagoran.mymovieapp.data.repository.AppDataView
import com.djekagoran.mymovieapp.data.api.repo.GenreRepo
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.utill.Constants
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class FavoriteMovieViewModel @Inject constructor(private val appDataRepository: AppDataView): ViewModel() {

    var dataMovie: LiveData<List<Movie>>? = null

    private val _dataGenre = MutableLiveData<ArrayList<Genre>>()
    val dataGenre: LiveData<ArrayList<Genre>> = _dataGenre

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    private val disposable = CompositeDisposable()

    fun getFavoriteMovies() {
        dataMovie = appDataRepository.favoriteMovie
        loadDataGenres()
    }

    fun favorite(movie: Movie, isChecked: Boolean) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (isChecked) appDataRepository.saveFavoriteMovie(movie) else appDataRepository.deleteFavoriteMovie(movie)
            } catch (e: Exception) {
                _errorMsg.postValue(e.message)
            }
        }
    }

    private fun loadDataGenres() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = appDataRepository.loadMovieGenres(Constants.API_KEY)
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
                appDataRepository.saveGenre(genreRepo.genres!!.toList())
            } catch (e: Exception) {
                _errorMsg.postValue(e.message)
            }
        }

    }

    private suspend fun onErrorGenresLoad(throwable: Throwable) {
        appDataRepository.genres.collect {
            if (it.isNotEmpty()) {
                _dataGenre.postValue(ArrayList(it))
            } else {
                _errorMsg.postValue(throwable.message)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}
