package me.alfredobejarano.brastlewarkarchitecturecomponents.di

import android.app.Application

object Injector {
    @Volatile
    private lateinit var app: Application

    fun init(application: Application) {
        app = application
    }

    val component: BrastlewarkApplicationComponent by lazy {
        DaggerBrastlewarkApplicationComponent.builder()
            .application(app)
            .dataSourceModule(DataSourceModule(app))
            .build()
    }
}