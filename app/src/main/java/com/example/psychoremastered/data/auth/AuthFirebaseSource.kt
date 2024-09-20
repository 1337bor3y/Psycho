package com.example.psychoremastered.data.auth

import com.example.psychoremastered.data.auth.model.AuthUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthFirebaseSource @Inject constructor(
    private val auth: FirebaseAuth
): AuthApi {

    override suspend fun signInWithCredential(idToken: String): AuthUser? {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        val auth = auth.signInWithCredential(firebaseCredential).await()
        return auth.user?.run {
            AuthUser(
                userId = uid,
                isNewUser = auth.additionalUserInfo?.isNewUser ?: false,
                email = email,
                displayName = displayName,
                profilePictureUri = photoUrl.toString()
            )
        }
    }

    override suspend fun createUserWithEmailAndPassword(
        authEmail: String,
        authPassword: String
    ): AuthUser? {
        val user = auth.createUserWithEmailAndPassword(authEmail, authPassword).await().user

        return user?.run {
            AuthUser(
                userId = uid,
                isNewUser = true,
                email = email,
                displayName = displayName,
                profilePictureUri = photoUrl.toString()
            )
        }
    }

    override suspend fun signInWithEmailAndPassword(
        authEmail: String,
        authPassword: String
    ): AuthUser? {
        val user = auth.signInWithEmailAndPassword(authEmail, authPassword).await().user

        return user?.run {
            AuthUser(
                userId = uid,
                isNewUser = false,
                email = email,
                displayName = displayName,
                profilePictureUri = photoUrl.toString()
            )
        }
    }
}