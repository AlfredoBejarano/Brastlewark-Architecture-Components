package me.alfredobejarano.brastlewarkarchitecturecomponents.datasource.local

import android.app.Application
import android.content.Context
import java.util.*

/**
 * Class that serves as a wrapper for accessing the app SharedPreferences. An XML file that
 * contains the timestamps for various app caches.
 */
class GnomeCacheTimeStampDataSource(private val application: Application) {
    private companion object {
        const val GNOME_CACHE_KEY = "gnome_cahe"
        const val SHARED_FILE_NAME = "cache"
        const val CACHE_DURATION = 15 // Cache for 15 minutes.
    }

    /**
     * Singleton reference to this app shared preferences.
     */
    private val preferences by lazy {
        application.getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Generates a timestamp referencing the time were the stored cache must expire.
     */
    private fun generateCacheTimeStamp() = Calendar.getInstance(Locale.getDefault()).run {
        add(Calendar.MINUTE, CACHE_DURATION)
        timeInMillis
    }

    /**
     * Retrieves the current system time in milliseconds from the epoch.
     */
    private fun getCurrentTimeInMillis() = Calendar.getInstance(Locale.getDefault()).timeInMillis

    /**
     * Stores a timestamp cache in the shared preferences XML file.
     * @param key Cache key for the XML file.
     */
    private fun storeCacheTimeStamp() = preferences.edit()?.apply {
        putLong(GNOME_CACHE_KEY, generateCacheTimeStamp())
    }?.apply()

    /**
     * Checks if the cache timestamp for the given value is valid against the current system time.
     */
    private fun isCacheValid(): Boolean {
        val currentTimeInMillis = getCurrentTimeInMillis()
        val cacheTimeInMillis = preferences.getLong(GNOME_CACHE_KEY, currentTimeInMillis)
        return currentTimeInMillis < cacheTimeInMillis
    }

    /**
     * Checks if the gnome cache is valid.
     * @return true if the cache is still valid.
     */
    fun isGnomeCacheValid() = isCacheValid()

    /**
     * Generates the gnome cache timestamp.
     */
    fun generateGnomeCache() = storeCacheTimeStamp()
}