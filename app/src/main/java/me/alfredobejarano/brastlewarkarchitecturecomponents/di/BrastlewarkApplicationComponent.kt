package me.alfredobejarano.brastlewarkarchitecturecomponents.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataSourceModule::class])
interface BrastlewarkApplicationComponent {
    @Component.Builder
    interface Builder {
        fun build(): BrastlewarkApplicationComponent
        @BindsInstance
        fun application(app: Application): Builder

        fun dataSourceModule(module: DataSourceModule): Builder
    }
}