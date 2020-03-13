package me.alfredobejarano.brastlewarkarchitecturecomponents.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import me.alfredobejarano.brastlewarkarchitecturecomponents.utils.within

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
    val weight: Float? = 0f,
    @Expose
    @SerializedName("height")
    val height: Float? = 0f,
    @Expose
    @SerializedName("hair_color")
    val hairColor: String? = "",
    @Expose
    @SerializedName("professions")
    val professions: List<String>? = emptyList(),
    @Expose
    @SerializedName("friends")
    val friends: List<String>? = emptyList()
) {
    fun meetsFilters(filters: FilterSettings) = filters.let {
        val hasAge = age.within(it.ageRange)
        val hasWeight = weight?.toInt().within(it.weightRange)
        val hasHeight = height?.toInt().within(it.heightRange)
        val hasFriends = friends?.size.within(it.friendsRange)
        val hasProfessions = professions?.containsAll(it.professions) == true
        val hasHairColor =
            hairColor?.let { color -> it.hairColors.contains(color) } ?: run { false }

        hasAge && hasWeight && hasHeight && hasFriends && hasProfessions && hasHairColor
    }
}