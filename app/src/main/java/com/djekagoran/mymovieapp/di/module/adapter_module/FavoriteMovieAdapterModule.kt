package com.djekagoran.mymovieapp.di.module.adapter_module

import com.djekagoran.mymovieapp.ui.favorite_movie.favoritemovie.FavoriteMovieRecycleViewAdapter
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
class FavoriteMovieAdapterModule {

    @Provides
    fun getListAdapter(picasso: Picasso): FavoriteMovieRecycleViewAdapter {
        return FavoriteMovieRecycleViewAdapter (picasso)
    }

}
