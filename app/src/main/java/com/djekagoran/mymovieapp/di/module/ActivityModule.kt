package com.djekagoran.mymovieapp.di.module

import com.djekagoran.mymovieapp.di.module.fragment_module.*
import com.djekagoran.mymovieapp.ui.main.MainActivity
import com.djekagoran.mymovieapp.di.scope.ActivityScope
import com.djekagoran.mymovieapp.ui.detail_crew.DetailCrewActivity
import com.djekagoran.mymovieapp.ui.detail_movie.DetailMovieActivity
import com.djekagoran.mymovieapp.ui.favorite_crew.FavoriteCrewActivity
import com.djekagoran.mymovieapp.ui.favorite_movie.FavoriteMovieActivity
import com.djekagoran.mymovieapp.ui.recently_movie.RecentlyMovieActivity
import com.djekagoran.mymovieapp.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeSplash(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentMainModule::class])
    abstract fun contributeMain(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector (modules = [FragmentMainModule::class])
    abstract fun contributeDetailMovie(): DetailMovieActivity

    @ActivityScope
    @ContributesAndroidInjector (modules = [FragmentCrewModule::class])
    abstract fun contributeDetailCrew(): DetailCrewActivity

    @ActivityScope
    @ContributesAndroidInjector (modules = [FragmentFavMovieModule::class])
    abstract fun contributeFavoriteMovie(): FavoriteMovieActivity

    @ActivityScope
    @ContributesAndroidInjector (modules = [FragmentFavCrewModule::class])
    abstract fun contributeActors(): FavoriteCrewActivity

    @ActivityScope
    @ContributesAndroidInjector (modules = [FragmentRecentlyMoviesModule::class])
    abstract fun contributeRecently(): RecentlyMovieActivity

}