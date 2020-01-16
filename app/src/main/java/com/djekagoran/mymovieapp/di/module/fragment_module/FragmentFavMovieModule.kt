package com.djekagoran.mymovieapp.di.module.fragment_module

import androidx.lifecycle.ViewModel
import com.djekagoran.mymovieapp.di.ViewModelKey
import com.djekagoran.mymovieapp.di.module.adapter_module.FavoriteMovieAdapterModule
import com.djekagoran.mymovieapp.di.scope.FragmentScope
import com.djekagoran.mymovieapp.ui.favorite_movie.favoritemovie.FavoriteMovieFragment
import com.djekagoran.mymovieapp.ui.favorite_movie.favoritemovie.FavoriteMovieViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FragmentFavMovieModule {

    @FragmentScope
    @ContributesAndroidInjector (modules = [FavoriteMovieAdapterModule::class])
    abstract fun contributeFavorite(): FavoriteMovieFragment

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteMovieViewModel::class)
    abstract fun bindViewModelFavorite(viewModel: FavoriteMovieViewModel): ViewModel

}
