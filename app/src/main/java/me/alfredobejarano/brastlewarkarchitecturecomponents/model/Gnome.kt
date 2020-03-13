package me.alfredobejarano.brastlewarkarchitecturecomponents.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Model class that contains the data of a Gnome.
 */
@Entity(tableName = "Gnomes")
data class Gnome(
    @ColumnInfo(name = "pk")
    @Expose
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int? = -1,
    @Expose
    @SerializedName("name")
    val name: String? = "",
    @Expose
    @SerializedName("thumbnail")
    val thumbnailUrl: String? = "",
    @Expose
    @SerializedName("age")
    val age: Int? = 0,
    @Expose
    @SerializedName("weight")
    val weight: Int? = 0,
    @Expose
    @SerializedName("height")
    val height: Int? = 0,
    @Expose
    @SerializedName("hair_color")
    val hairColor: String? = "",
    @Expose
    @SerializedName("professions")
    val professions: List<String>? = emptyList(),
    @Expose
    @SerializedName("friends")
    val friends: List<String>? = emptyList()
)