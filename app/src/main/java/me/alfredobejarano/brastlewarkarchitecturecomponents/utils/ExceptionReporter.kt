package me.alfredobejarano.brastlewarkarchitecturecomponents.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object ExceptionReporter {
    private val mutableExceptionLiveData: MutableLiveData<Exception> = MutableLiveData()
    val exceptionLiveData = mutableExceptionLiveData as LiveData<Exception>

    /**
     * Reports an exception to the event LiveData.
     */
    fun reportException(e: Exception) = mutableExceptionLiveData.postValue(e)
}