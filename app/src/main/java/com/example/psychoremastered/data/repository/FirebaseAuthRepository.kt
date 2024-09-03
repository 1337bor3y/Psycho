package com.example.psychoremastered.data.repository

import com.example.psychoremastered.domain.model.User
import com.example.psychoremastered.domain.repository.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(): AuthRepository {
    private val auth = Firebase.auth

    override suspend fun signInWithCredential(idToken: String): User? {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        val user = auth.signInWithCredential(firebaseCredential).await().user

        return user?.run {
            User(
                userId = uid,
                displayName = displayName,
                profilePictureUri = photoUrl.toString()
            )
        }
    }
}