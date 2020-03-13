package me.alfredobejarano.brastlewarkarchitecturecomponents.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.alfredobejarano.brastlewarkarchitecturecomponents.model.FilterSettings
import me.alfredobejarano.brastlewarkarchitecturecomponents.model.Gnome
import me.alfredobejarano.brastlewarkarchitecturecomponents.repository.GnomeRepository
import me.alfredobejarano.brastlewarkarchitecturecomponents.utils.rangeFrom
import me.alfredobejarano.brastlewarkarchitecturecomponents.utils.setFrom
import me.alfredobejarano.brastlewarkarchitecturecomponents.utils.setOf
import javax.inject.Inject

class GnomeListViewModel @Inject constructor(private val gnomeRepository: GnomeRepository) :
    ViewModel() {
    private var gnomes = listOf<Gnome>()

    private val gnomesMutableLiveData = MutableLiveData<List<Gnome>>()
    val gnomesLiveData = gnomesMutableLiveData as LiveData<List<Gnome>>

    fun getGnomePopulation() = GlobalScope.launch(Dispatchers.IO) {
        gnomesMutableLiveData.postValue(if (gnomes.isNotEmpty()) gnomes else getGnomesFromRepository())
    }

    private suspend fun getGnomesFromRepository() = gnomeRepository.getGnomePopulation()?.run {
        if (isNotEmpty()) gnomes = sortedBy { it.name }
        gnomes
    } ?: gnomes

    fun getFilterSettings() = liveData {
        emit(FilterSettings(
            gnomes.rangeFrom({ it?.age }) { it.first?.age to it.second?.age },
            gnomes.rangeFrom({ it?.weight }) { it.first?.weight to it.second?.weight },
            gnomes.rangeFrom({ it?.height }) { it.first?.height to it.second?.height },
            gnomes.rangeFrom({ it?.friends?.size }) { it.first?.friends?.size to it.second?.friends?.size },
            gnomes.setFrom { it.professions },
            gnomes.setOf { it.hairColor }
        ))
    }

    fun filter(
        ageRange: IntRange,
        weightRange: IntRange,
        heightRange: IntRange,
        friendsRange: IntRange,
        professions: Set<String>,
        hairColors: Set<String>
    ) = GlobalScope.launch(Dispatchers.IO) {
        val filters = FilterSettings(
            ageRange,
            weightRange,
            heightRange,
            friendsRange,
            professions,
            hairColors
        )
        gnomesMutableLiveData.postValue(gnomes.filter { it.meetsFilters(filters) })
    }
}