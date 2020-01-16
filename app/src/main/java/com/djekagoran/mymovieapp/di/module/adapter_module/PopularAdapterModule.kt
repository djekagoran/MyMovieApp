package com.djekagoran.mymovieapp.di.module.adapter_module

import com.djekagoran.mymovieapp.ui.main.popular.PopularRecycleViewAdapter
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
class PopularAdapterModule {

    @Provides
    fun getListAdapter(picasso: Picasso): PopularRecycleViewAdapter {
        return PopularRecycleViewAdapter(picasso)
    }

}
