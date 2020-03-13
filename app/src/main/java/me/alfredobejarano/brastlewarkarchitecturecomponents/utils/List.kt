package me.alfredobejarano.brastlewarkarchitecturecomponents.utils

fun <T> List<T>.rangeFrom(
    sorter: (element: T?) -> Int?,
    block: (elements: Pair<T?, T?>) -> Pair<Int?, Int?>
): IntRange {
    val list = sortedBy { sorter(it) }
    val rangeValues = block(Pair(list.first(), list.last()))
    return (rangeValues.first ?: 0)..(rangeValues.second ?: 0)
}

fun <T> List<T>.setFrom(block: (element: T) -> List<String>?): Set<String> {
    var set = setOf<String>()
    if (isNotEmpty()) forEach { block(it)?.run { set = set.plus(this) } }
    return set
}

fun <T> List<T>.setOf(block: (element: T) -> String?): Set<String> {
    var set = setOf<String>()
    if (isNotEmpty()) forEach { block(it)?.run { set = set.plus(this) } }
    return set
}