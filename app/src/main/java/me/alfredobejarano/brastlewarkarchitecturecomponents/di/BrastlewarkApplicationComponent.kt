package me.alfredobejarano.brastlewarkarchitecturecomponents.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import me.alfredobejarano.brastlewarkarchitecturecomponents.ui.GnomeListFragment
import me.alfredobejarano.brastlewarkarchitecturecomponents.ui.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [DataSourceModule::class, ViewModelModule::class])
interface BrastlewarkApplicationComponent {
    @Component.Builder
    interface Builder {
        fun build(): BrastlewarkApplicationComponent
        @BindsInstance
        fun application(app: Application): Builder

        fun dataSourceModule(module: DataSourceModule): Builder
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: GnomeListFragment)
}