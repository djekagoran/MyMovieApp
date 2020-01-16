package com.djekagoran.mymovieapp.di.module.adapter_module

import com.djekagoran.mymovieapp.ui.favorite_crew.favoritecrew.FavoriteCrewRecycleViewAdapter
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
class FavoriteCrewAdapterModule {

    @Provides
    fun getListAdapter(picasso: Picasso): FavoriteCrewRecycleViewAdapter {
        return FavoriteCrewRecycleViewAdapter(picasso)
    }

}
