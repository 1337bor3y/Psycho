package com.example.psychoremastered.domain.repository

import com.example.psychoremastered.domain.model.User

interface AuthRepository {

    suspend fun signInWithCredential(idToken: String): User?

    suspend fun createUserWithEmailAndPassword(authEmail: String, authPassword: String): User?

    suspend fun signInWithEmailAndPassword(authEmail: String, authPassword: String): User?

    suspend fun getCurrentUser(): User?

    suspend fun signOut()
}