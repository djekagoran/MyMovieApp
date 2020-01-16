package com.djekagoran.mymovieapp.di.module.adapter_module

import com.djekagoran.mymovieapp.ui.recently_movie.recentlymovie.RecentlyMovieRecycleViewAdapter
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
class RecentlyMovieAdapterModule {

    @Provides
    fun getListAdapter(picasso: Picasso): RecentlyMovieRecycleViewAdapter {
        return RecentlyMovieRecycleViewAdapter (picasso)
    }

}
