package com.example.psychoremastered.data.remote

import android.net.Uri
import com.example.psychoremastered.core.util.Constants
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ImageStorageFirebase @Inject constructor(
    storage: FirebaseStorage
): ImageStorageApi {
    private val reference = storage.getReference(Constants.FIREBASE_STORAGE_IMAGE_PATH)

    override suspend fun saveImage(imageUri: Uri): String {
        if (imageUri.toString().contains("content://")) {
            val imgRef = reference.child(imageUri.lastPathSegment.toString())
            imgRef.putFile(imageUri).await()
            return imgRef.downloadUrl.await().toString()
        } else {
            return imageUri.toString()
        }
    }
}