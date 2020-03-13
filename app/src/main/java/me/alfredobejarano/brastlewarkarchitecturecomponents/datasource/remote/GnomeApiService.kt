package me.alfredobejarano.brastlewarkarchitecturecomponents.datasource.remote

import me.alfredobejarano.brastlewarkarchitecturecomponents.BuildConfig
import me.alfredobejarano.brastlewarkarchitecturecomponents.model.Brastlewark
import retrofit2.http.GET

/**
 * Retrofit interface that provides access to the app endpoints.
 */
interface GnomeApiService {
    /**
     * Retrieves the population list from Brastlewark endpoints.
     */
    @GET(BuildConfig.SERVER_URL)
    suspend fun getBrastlewarkPopulation(): Brastlewark
}