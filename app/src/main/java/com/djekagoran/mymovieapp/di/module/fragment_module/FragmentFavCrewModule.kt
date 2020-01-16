package com.djekagoran.mymovieapp.di.module.fragment_module

import androidx.lifecycle.ViewModel
import com.djekagoran.mymovieapp.di.ViewModelKey
import com.djekagoran.mymovieapp.di.module.adapter_module.FavoriteCrewAdapterModule
import com.djekagoran.mymovieapp.di.scope.FragmentScope
import com.djekagoran.mymovieapp.ui.favorite_crew.favoritecrew.FavoriteCrewFragment
import com.djekagoran.mymovieapp.ui.favorite_crew.favoritecrew.FavoriteCrewViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FragmentFavCrewModule {

    @FragmentScope
    @ContributesAndroidInjector (modules = [FavoriteCrewAdapterModule::class])
    abstract fun contributeCrew(): FavoriteCrewFragment

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteCrewViewModel::class)
    abstract fun bindViewModelCrew(viewModel: FavoriteCrewViewModel): ViewModel

}
