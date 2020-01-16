package com.djekagoran.mymovieapp.data

import com.djekagoran.mymovieapp.data.api.APIInterface
import com.djekagoran.mymovieapp.data.local.room.CrewDao
import com.djekagoran.mymovieapp.data.local.room.MovieDao
import com.djekagoran.mymovieapp.data.model.Crew
import com.djekagoran.mymovieapp.data.model.Genre
import com.djekagoran.mymovieapp.data.model.Movie
import com.djekagoran.mymovieapp.data.model.MovieType
import kotlinx.coroutines.flow.flow
import java.util.ArrayList
import javax.inject.Inject
class AppDataManager @Inject
constructor(private val apiInterface: APIInterface,
            private val movieDao: MovieDao,
            private val actorDao: CrewDao) : DataManager {

    override suspend fun loadMoviePopular(apiKey: String, page: Int) = apiInterface.loadMoviePopular(apiKey, page)

    override suspend fun loadMovieTopRated(apiKey: String, page: Int) = apiInterface.loadMovieTopRated(apiKey, page)

    override suspend fun loadMovieGenres(apiKey: String) = apiInterface.loadMovieGenres(apiKey)

    override suspend fun loadMovieCrew(movie_id: Int, apiKey: String) = apiInterface.loadMovieCrew(movie_id, apiKey)

    override suspend fun loadMovieRecommended(movie_id: Int, apiKey: String) = apiInterface.loadMovieRecommended(movie_id, apiKey)

    override fun loadSearch(apiKey: String, query: String, page: Int) = apiInterface.loadSearch(apiKey, query, page)

    override suspend fun loadActorMovie(person_id: Int, apiKey: String) = apiInterface.loadActorMovie(person_id, apiKey)

    override val popular =  flow { emit(movieDao.getAllMoviesByType(MovieType.POPULAR.name)) }
    override val topRated = flow { emit(movieDao.getAllMoviesByType(MovieType.TOP_RATED.name)) }
    override val genres = flow { emit(movieDao.getAllGenres()) }

    override suspend fun savePopular(list: ArrayList<Movie>) {
        movieDao.deleteAllMoviesByType(MovieType.POPULAR.name)
        for (m in list) {
            m.movie_type = MovieType.POPULAR
            movieDao.insert(m)
        }
    }

    override suspend fun saveTopRated(list: ArrayList<Movie>) {
        movieDao.deleteAllMoviesByType(MovieType.TOP_RATED.name)
        for (m in list) {
            m.movie_type = MovieType.TOP_RATED
            movieDao.insert(m)
        }
    }

    override suspend fun saveGenre(list: List<Genre>) {
        movieDao.insertAllGenre(list)
    }

    override val favoriteMovie = movieDao.getFavoriteMovies(MovieType.FAVORITE.name)
    override val favoriteCrew = actorDao.actors
    override val recentlyMovie = movieDao.getRecentlyMovies(MovieType.RECENTLY.name)

    override suspend fun saveFavoriteMovie(m: Movie) {
        m.movie_type = MovieType.FAVORITE
        m.create_time = System.currentTimeMillis()
        movieDao.insert(m)
    }

    override suspend fun saveFavoriteCrew(m: Crew){
        m.create_time = System.currentTimeMillis()
        actorDao.insert(m)
    }


    override suspend fun saveRecently(m: Movie) {
        m.movie_type = MovieType.RECENTLY
        m.create_time = System.currentTimeMillis()
        movieDao.insert(m)
    }


    override suspend fun deleteFavoriteMovie(m: Movie) = movieDao.delete(m)

    override suspend fun deleteFavoriteCrew(m: Crew) = actorDao.delete(m)


    override fun countFavoriteMovie(m: Movie) = flow { emit(movieDao.countFavorite(MovieType.FAVORITE.name, m.id)) }

    override fun countFavoriteCrew(m: Crew) = flow { emit(actorDao.countFavoriteCrew(m.id)) }


}

