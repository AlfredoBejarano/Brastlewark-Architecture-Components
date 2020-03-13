package me.alfredobejarano.brastlewarkarchitecturecomponents.repository

import me.alfredobejarano.brastlewarkarchitecturecomponents.utils.ExceptionReporter
import me.alfredobejarano.brastlewarkarchitecturecomponents.datasource.local.GnomeCacheTimeStampDataSource
import me.alfredobejarano.brastlewarkarchitecturecomponents.datasource.local.GnomeDao
import me.alfredobejarano.brastlewarkarchitecturecomponents.datasource.remote.GnomeApiService
import javax.inject.Inject

/**
 * Class that serves as the single source of truth for the gnome population.
 */
class GnomeRepository @Inject constructor(
    private val localDataSource: GnomeDao,
    private val remoteDataSource: GnomeApiService,
    private val cacheDataSource: GnomeCacheTimeStampDataSource
) {
    /**
     * Executes a given suspend block and returns its result, if an exception is thrown, reports
     * the exception and returns null.
     * @param block Suspend function to execute.
     * @return Result of the block code.
     */
    private suspend fun <T> interact(block: suspend () -> T?) = try {
        block()
    } catch (e: Exception) {
        ExceptionReporter.reportException(e)
        null
    }

    /**
     * Retrieves the gnome population from the network.
     * @return [Pair] class containing the gnomes list or an exception, if any.
     */
    private suspend fun getGnomePopulationFromNetwork() =
        interact { remoteDataSource.getBrastlewarkPopulation().population }

    /**
     * Retrieves the gnome population from the cache database.
     * @return [Pair] class containing the gnomes list or an exception, if any.
     */
    private suspend fun getGnomePopulationFromLocal() = interact {
        localDataSource.getAllGnomes()
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

    /**
     * Searches in the cache a Gnome with the given name.
     * @param name Name of the gnome.
     * @return Gnome found in the system.
     */
    suspend fun getGnome(name: String) = interact { localDataSource.getGnomeByName(name) }
}