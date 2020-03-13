package me.alfredobejarano.brastlewarkarchitecturecomponents.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Brastlewark(
    @Expose
    @SerializedName("Brastlewark")
    val population: List<Gnome>? = emptyList()
)