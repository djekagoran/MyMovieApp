package com.djekagoran.mymovieapp.ui.main.search

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djekagoran.mymovieapp.data.DataManager
import com.djekagoran.mymovieapp.data.api.repo.GenreRepo
import com.djekagoran.mymovieapp.data.api.repo.SearchRepo
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.data.model.MovieType
import com.djekagoran.mymovieapp.utill.Constants
import com.djekagoran.mymovieapp.utill.RxSearchObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val appDataManager: DataManager): ViewModel() {

    private var searchPage = 1

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

    private val disposable = CompositeDisposable()

    fun getSearchMovies(searchView: SearchView) {
        loadData(searchView)
        loadDataGenres()
    }

    private fun loadData(searchView: SearchView) {

        // todo replace rxJava with coroutines

        disposable.add(
            RxSearchObservable.fromView(searchView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter { it.isNotEmpty() }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { _loading.value = true }
                .distinctUntilChanged()
                .observeOn(Schedulers.io())
                .switchMap{ appDataManager.loadSearch(Constants.API_KEY, it,1) }
                .observeOn(AndroidSchedulers.mainThread())
                .map{
                    for (movie in it.results!!) {
                        movie.movie_type = MovieType.SEARCH
                    }
                    it
                }
                .subscribe ( { onSearchLoaded(it) }, { onErrorLoadSearch(it) }))
    }

    private fun onSearchLoaded(repo: SearchRepo) {
        _loading.value = false
        if (repo.results?.isNotEmpty()!!) {
            _dataMovie.value = repo.results
        } else {
            _toastMsg.value = "No movies found"
        }
    }

    private fun onErrorLoadSearch(throwable: Throwable) {
        _loading.value = false
        _toastMsg.value = throwable.message
    }

    fun searchLoadMore(query: String) {
        _loadingMore.value = true
        searchPage++
        disposable.add(appDataManager.loadSearch(Constants.API_KEY, query, searchPage)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                for (movie in it.results!!) {
                    movie.movie_type = MovieType.SEARCH
                }
                it
            }
            .subscribe ( { onSearchLoadMore(it) }, { onErrorLoadMore(it) }))
    }

    private fun onSearchLoadMore(repo: SearchRepo) {
        _loadingMore.value = false
        if (repo.results!!.isNotEmpty()) {
            _dataMovie.value!!.addAll(repo.results!!)
            _dataMovie.value = _dataMovie.value
        } else {
            _toastMsg.value = "No more movies"
        }
    }

    private fun onErrorLoadMore(throwable: Throwable) {
        searchPage --
        _loadingMore.value = false
        _toastMsg.value = throwable.message
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

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}
