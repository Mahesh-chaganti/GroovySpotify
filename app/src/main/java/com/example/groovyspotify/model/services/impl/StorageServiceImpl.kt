package com.example.groovyspotify.model.services.impl

import android.net.Uri
import com.example.groovyspotify.model.services.StorageService
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(): StorageService {
    override suspend fun uploadImageToStorage(imageUri: Uri) {
    }
}