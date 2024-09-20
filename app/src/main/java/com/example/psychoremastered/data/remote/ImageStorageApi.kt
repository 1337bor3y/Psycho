package com.example.psychoremastered.data.remote

import android.net.Uri

interface ImageStorageApi {

    suspend fun saveImage(imageUri: Uri): String
}