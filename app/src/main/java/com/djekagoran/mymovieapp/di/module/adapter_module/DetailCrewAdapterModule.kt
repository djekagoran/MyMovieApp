package com.djekagoran.mymovieapp.di.module.adapter_module

import com.djekagoran.mymovieapp.ui.detail_crew.detailcrew.DetailCrewRecycleViewAdapter
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
class DetailCrewAdapterModule {

    @Provides
    fun getListAdapter(picasso: Picasso): DetailCrewRecycleViewAdapter{
        return DetailCrewRecycleViewAdapter(picasso)
    }

}
