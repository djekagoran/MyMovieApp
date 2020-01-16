package com.djekagoran.mymovieapp.di.module.fragment_module

import androidx.lifecycle.ViewModel
import com.djekagoran.mymovieapp.di.ViewModelKey
import com.djekagoran.mymovieapp.di.module.adapter_module.RecentlyMovieAdapterModule
import com.djekagoran.mymovieapp.di.scope.FragmentScope
import com.djekagoran.mymovieapp.ui.recently_movie.recentlymovie.RecentlyMovieFragment
import com.djekagoran.mymovieapp.ui.recently_movie.recentlymovie.RecentlyMovieViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FragmentRecentlyMoviesModule {

    @FragmentScope
    @ContributesAndroidInjector (modules = [RecentlyMovieAdapterModule::class])
    abstract fun contributeRecently(): RecentlyMovieFragment

    @Binds
    @IntoMap
    @ViewModelKey(RecentlyMovieViewModel::class)
    abstract fun bindViewModelRecently(viewModel: RecentlyMovieViewModel): ViewModel

}
