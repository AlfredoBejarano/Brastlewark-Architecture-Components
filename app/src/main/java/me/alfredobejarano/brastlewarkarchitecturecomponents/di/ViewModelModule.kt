package me.alfredobejarano.brastlewarkarchitecturecomponents.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.alfredobejarano.brastlewarkarchitecturecomponents.viewmodel.GnomeDetailsViewModel
import me.alfredobejarano.brastlewarkarchitecturecomponents.viewmodel.GnomeListViewModel

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GnomeDetailsViewModel::class)
    abstract fun bindGnomeDetailsViewModel(viewModel: GnomeDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GnomeListViewModel::class)
    abstract fun bindGnomeListViewModel(viewModel: GnomeListViewModel): ViewModel
}