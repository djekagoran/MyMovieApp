package com.djekagoran.mymovieapp.di.module.adapter_module

import com.djekagoran.mymovieapp.ui.main.top_rated.TopRatedRecycleViewAdapter
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
class TopRatedAdapterModule {

    @Provides
    fun getListAdapter(picasso: Picasso): TopRatedRecycleViewAdapter {
        return TopRatedRecycleViewAdapter(picasso)
    }

}
