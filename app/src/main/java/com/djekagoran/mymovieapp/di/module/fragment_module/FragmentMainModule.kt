package com.djekagoran.mymovieapp.di.module.fragment_module

import androidx.lifecycle.ViewModel
import com.djekagoran.mymovieapp.di.ViewModelKey
import com.djekagoran.mymovieapp.di.module.adapter_module.DetailMovieAdapterModule
import com.djekagoran.mymovieapp.di.module.adapter_module.PopularAdapterModule
import com.djekagoran.mymovieapp.di.module.adapter_module.SearchAdapterModule
import com.djekagoran.mymovieapp.di.module.adapter_module.TopRatedAdapterModule
import com.djekagoran.mymovieapp.di.scope.FragmentScope
import com.djekagoran.mymovieapp.ui.detail_movie.detailmovie.DetailMovieFragment
import com.djekagoran.mymovieapp.ui.detail_movie.detailmovie.DetailMovieViewModel
import com.djekagoran.mymovieapp.ui.main.favorite.FavoriteFragment
import com.djekagoran.mymovieapp.ui.main.favorite.FavoriteViewModel
import com.djekagoran.mymovieapp.ui.main.popular.PopularFragment
import com.djekagoran.mymovieapp.ui.main.popular.PopularViewModel
import com.djekagoran.mymovieapp.ui.main.search.SearchFragment
import com.djekagoran.mymovieapp.ui.main.search.SearchViewModel
import com.djekagoran.mymovieapp.ui.main.top_rated.TopRatedFragment
import com.djekagoran.mymovieapp.ui.main.top_rated.TopRatedViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FragmentMainModule {

    @FragmentScope
    @ContributesAndroidInjector (modules = [TopRatedAdapterModule::class])
    abstract fun contributeTopRated(): TopRatedFragment

    @FragmentScope
    @ContributesAndroidInjector (modules = [PopularAdapterModule::class])
    abstract fun contributePopular(): PopularFragment

    @FragmentScope
    @ContributesAndroidInjector (modules = [SearchAdapterModule::class])
    abstract fun contributeSearch(): SearchFragment

    @FragmentScope
    @ContributesAndroidInjector (modules = [DetailMovieAdapterModule::class])
    abstract fun contributeDetailMovie(): DetailMovieFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeFavoriteTab(): FavoriteFragment


    @Binds
    @IntoMap
    @ViewModelKey(TopRatedViewModel::class)
    abstract fun bindViewModelTopRated(viewModel: TopRatedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PopularViewModel::class)
    abstract fun bindViewModelPopular(viewModel: PopularViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindViewModelSearch(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailMovieViewModel::class)
    abstract fun bindViewModelDetailMovie(viewModel: DetailMovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindViewModelFavoriteTab(viewModel: FavoriteViewModel): ViewModel

}
