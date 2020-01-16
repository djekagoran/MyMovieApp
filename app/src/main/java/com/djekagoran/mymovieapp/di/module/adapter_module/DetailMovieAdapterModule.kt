package com.djekagoran.mymovieapp.di.module.adapter_module

import com.djekagoran.mymovieapp.ui.detail_movie.detailmovie.CrewRecycleViewAdapter
import com.djekagoran.mymovieapp.ui.detail_movie.detailmovie.RecommRecycleViewAdapter
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
class DetailMovieAdapterModule {

    @Provides
    fun getListAdapter(picasso: Picasso): CrewRecycleViewAdapter {
        return CrewRecycleViewAdapter(picasso)
    }

    @Provides
    fun getListAdapterRecomm(picasso: Picasso): RecommRecycleViewAdapter {
        return RecommRecycleViewAdapter(picasso)
    }
}
