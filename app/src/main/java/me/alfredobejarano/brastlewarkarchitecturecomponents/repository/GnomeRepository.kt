package me.alfredobejarano.brastlewarkarchitecturecomponents.repository

import me.alfredobejarano.brastlewarkarchitecturecomponents.datasource.local.GnomeCacheTimeStampDataSource
import me.alfredobejarano.brastlewarkarchitecturecomponents.datasource.local.GnomeDao
import me.alfredobejarano.brastlewarkarchitecturecomponents.datasource.remote.GnomeApiService
import java.lang.Exception

/**
 * Class that serves as the single source of truth for the gnome population.
 */
class GnomeRepository(
    private val localDataSource: GnomeDao,
    private val remoteDataSource: GnomeApiService,
    private val cacheDataSource: GnomeCacheTimeStampDataSource
) {
    /**
     * Retrieves the gnome population from the network.
     * @return [Pair] class containing the gnomes list or an exception, if any.
     */
    private suspend fun getGnomePopulationFromNetwork() = try {
        remoteDataSource.getBrastlewarkPopulation().population
    } catch (e: Exception) {
        null
    }

    /**
     * Retrieves the gnome population from the cache database.
     * @return [Pair] class containing the gnomes list or an exception, if any.
     */
    private suspend fun getGnomePopulationFromLocal() = try {
        localDataSource.getAllGnomes()
    } catch (e: Exception) {
        null
    }

    /**
     * Retrieves the gnome population from the best data source depending on the app cache.
     * @return [Pair] class containing the gnomes list or an exception, if any.
     */
    suspend fun getGnomePopulation() = if (cacheDataSource.isGnomeCacheValid()) {
        getGnomePopulationFromLocal()
    } else {
        getGnomePopulationFromNetwork()
    }
}