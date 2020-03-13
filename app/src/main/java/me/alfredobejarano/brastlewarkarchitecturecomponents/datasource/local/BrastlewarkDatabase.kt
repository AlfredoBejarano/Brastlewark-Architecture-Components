package me.alfredobejarano.brastlewarkarchitecturecomponents.datasource.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.alfredobejarano.brastlewarkarchitecturecomponents.BuildConfig
import me.alfredobejarano.brastlewarkarchitecturecomponents.model.Gnome

/**
 * Database that defines the app database schemas.
 */
@TypeConverters(StringListTypeConverter::class)
@Database(version = BuildConfig.VERSION_CODE, exportSchema = false, entities = [Gnome::class])
abstract class BrastlewarkDatabase : RoomDatabase() {
    companion object {
        /**
         * Name for this database.
         */
        private const val DATABASE_NAME = "BrastlewarkArch.db"

        /**
         * Singleton reference for this database.
         */
        private var INSTANCE: BrastlewarkDatabase? = null

        /**
         * Creates a [BrastlewarkDatabase] instance.
         *
         * @param app Application class that builds this instance.
         */
        private fun createInstance(app: Application): BrastlewarkDatabase =
            Room.databaseBuilder(app, BrastlewarkDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()

        /**
         * Returns the [Singleton Instance][INSTANCE] of this class.
         * If the instance is null, creates one and assigns it to the singleton instance.
         *
         * @param app Application class that builds this instance.
         */
        fun getInstance(app: Application) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: createInstance(app).also { INSTANCE = it }
        }
    }

    /**
     * Provides a [GnomeDao] instance.
     */
    abstract fun provideGnomeDao(): GnomeDao
}