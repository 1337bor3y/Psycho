package com.example.psychoremastered.data.auth

import com.example.psychoremastered.data.auth.model.AuthUser

interface AuthApi {

    suspend fun signInWithCredential(idToken: String): AuthUser?

    suspend fun createUserWithEmailAndPassword(
        authEmail: String,
        authPassword: String
    ): AuthUser?

    suspend fun signInWithEmailAndPassword(
        authEmail: String,
        authPassword: String
    ): AuthUser?
}