package com.djekagoran.mymovieapp.data.api

import com.djekagoran.mymovieapp.data.api.repo.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIInterface {

    @GET("3/movie/popular")
    suspend fun loadMoviePopular(@Query("api_key") apiKey: String, @Query("page") page: Int): PopularRepo

    @GET("3/movie/top_rated")
    suspend fun loadMovieTopRated(@Query("api_key") apiKey: String, @Query("page") page: Int): TopRatedRepo

    @GET("3/genre/movie/list")
    suspend fun loadMovieGenres(@Query("api_key") apiKey: String): GenreRepo

    @GET("3/movie/{movie_id}/credits")
    suspend fun loadMovieCrew(@Path("movie_id") movie_id: Int, @Query("api_key") apiKey: String): CrewRepo

    @GET("3/movie/{movie_id}/recommendations")
    suspend fun loadMovieRecommended(@Path("movie_id") movie_id: Int, @Query("api_key") apiKey: String): RecommendedRepo


    @GET("3/search/movie/")
    fun loadSearch(@Query("api_key") apiKey: String, @Query("query") query: String, @Query("page") page: Int): Observable<SearchRepo>

    @GET("3/person/{person_id}/movie_credits")
    suspend fun loadActorMovie(@Path("person_id") person_id: Int, @Query("api_key") apiKey: String): ActorMovieRepo

}
