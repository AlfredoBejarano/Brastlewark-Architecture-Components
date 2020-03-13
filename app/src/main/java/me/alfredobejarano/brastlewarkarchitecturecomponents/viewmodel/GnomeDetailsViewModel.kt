package me.alfredobejarano.brastlewarkarchitecturecomponents.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import me.alfredobejarano.brastlewarkarchitecturecomponents.repository.GnomeRepository
import javax.inject.Inject

class GnomeDetailsViewModel @Inject constructor(private val repository: GnomeRepository) :
    ViewModel() {
    fun getGnomeDetails(name: String) = liveData(Dispatchers.IO) {
        emit(repository.getGnome(name))
    }
}