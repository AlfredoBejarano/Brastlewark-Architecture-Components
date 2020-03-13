package me.alfredobejarano.brastlewarkarchitecturecomponents.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.alfredobejarano.brastlewarkarchitecturecomponents.model.Gnome

/**
 * Dao interface that provides ease of access for the [Gnome] database.
 */
@Dao
interface GnomeDao {
    /**
     * Inserts a [Gnome] record in the table.
     * If the record already exists, it gets replaced.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createOrUpdate(gnone: Gnome)

    /**
     * Retrieves a [Gnome] record by its name.
     */
    @Query("SELECT * FROM Gnomes WHERE name = :name LIMIT 1")
    suspend fun getGnomeByName(name: String): Gnome?

    /**
     * Deletes all records from the [Gnome] table.
     */
    @Query("DELETE FROM Gnomes")
    suspend fun nukeTable()
}