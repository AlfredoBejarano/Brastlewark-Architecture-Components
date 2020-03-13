package me.alfredobejarano.brastlewarkarchitecturecomponents.utils

fun Int?.within(range: IntRange) = this?.let { range.contains(it) } ?: run { false }