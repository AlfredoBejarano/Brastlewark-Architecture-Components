package me.alfredobejarano.brastlewarkarchitecturecomponents.model

data class FilterSettings(
    val ageRange: IntRange,
    val weightRange: IntRange,
    val heightRange: IntRange,
    val friendsRange: IntRange,
    val professions: Set<String>,
    val hairColors: Set<String>
)