package me.alfredobejarano.brastlewarkarchitecturecomponents.datasource.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListTypeConverter {
    @TypeConverter
    fun stringToList(value: String): List<String> =
        Gson().fromJson(value, object : TypeToken<ArrayList<String>>() {}.type)

    @TypeConverter
    fun fromString(value: List<String>): String = Gson().toJson(value)
}