package com.example.groovyspotify.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groovyspotify.model.services.LogService
import com.example.groovyspotify.model.services.common.Event
import com.example.groovyspotify.model.services.common.HandleException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class ParentViewModel @Inject constructor(private val logService: LogService) : ViewModel() {
    var popupNotification = mutableStateOf<Event<String>?>(Event(""))
        private set


    fun launchCatching( block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->

                logService.logNonFatalCrash(throwable)
            },
            block = block
        )



}