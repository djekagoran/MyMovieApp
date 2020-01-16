package com.djekagoran.mymovieapp.di.module.adapter_module

import com.djekagoran.mymovieapp.ui.main.search.SearchRecycleViewAdapter
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
class SearchAdapterModule {

    @Provides
    fun getListAdapter(picasso: Picasso): SearchRecycleViewAdapter {
        return SearchRecycleViewAdapter (picasso)
    }

}
