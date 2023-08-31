package com.example.groovyspotify.model.services

import android.net.Uri

interface StorageService {

    suspend fun uploadImageToStorage(imageUri: Uri)


}