package me.alfredobejarano.brastlewarkarchitecturecomponents.viewmodel

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import me.alfredobejarano.brastlewarkarchitecturecomponents.repository.GnomeRepository

class GnomeDetailsViewModel(private val repository: GnomeRepository) {
    fun getGnomeDetails(name: String) = liveData(Dispatchers.IO) {
        emit(repository.getGnome(name))
    }
}