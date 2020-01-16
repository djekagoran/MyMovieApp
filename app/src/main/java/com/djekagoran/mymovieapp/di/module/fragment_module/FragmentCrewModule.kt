package com.djekagoran.mymovieapp.di.module.fragment_module

import androidx.lifecycle.ViewModel
import com.djekagoran.mymovieapp.di.ViewModelKey
import com.djekagoran.mymovieapp.di.module.adapter_module.DetailCrewAdapterModule
import com.djekagoran.mymovieapp.di.scope.FragmentScope
import com.djekagoran.mymovieapp.ui.detail_crew.detailcrew.DetailCrewFragment
import com.djekagoran.mymovieapp.ui.detail_crew.detailcrew.DetailCrewViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FragmentCrewModule {

    @FragmentScope
    @ContributesAndroidInjector (modules = [DetailCrewAdapterModule::class])
    abstract fun contributeDetailActor(): DetailCrewFragment

    @Binds
    @IntoMap
    @ViewModelKey(DetailCrewViewModel::class)
    abstract fun bindViewModelDetailActor(viewModel: DetailCrewViewModel): ViewModel

}
