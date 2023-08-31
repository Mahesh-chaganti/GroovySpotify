package com.example.groovyspotify.model.services.common

import android.util.Log
import androidx.compose.runtime.mutableStateOf

fun HandleException(exception: Exception? = null, customMessage: String = ""): String{




    exception?.printStackTrace()
    val errorMsg = exception?.localizedMessage ?: ""
   return  if (customMessage.isEmpty()) errorMsg else "$customMessage: $errorMsg"

}