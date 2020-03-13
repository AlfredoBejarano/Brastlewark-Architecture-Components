package me.alfredobejarano.brastlewarkarchitecturecomponents.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Observes the value of a given [LiveData] and returns its value only if it is non-nullable.
 * @param T Expected result from the observation.
 * @param liveData LiveData to observe.
 * @param onSuccess Function called when the result is not null.
 */
fun <T> LifecycleOwner.observeSafely(liveData: LiveData<T>, onSuccess: (value: T) -> Unit) =
    liveData.observe(this, Observer { it?.run(onSuccess) })