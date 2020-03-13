package me.alfredobejarano.brastlewarkarchitecturecomponents

import android.app.Application
import me.alfredobejarano.brastlewarkarchitecturecomponents.di.Injector

class BrastlewarkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
    }
}