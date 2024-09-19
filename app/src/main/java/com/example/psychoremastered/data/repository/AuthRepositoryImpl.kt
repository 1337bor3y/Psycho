package com.example.psychoremastered.data.repository

import com.example.psychoremastered.data.auth.AuthApi
import com.example.psychoremastered.data.mappers.toUser
import com.example.psychoremastered.domain.model.User
import com.example.psychoremastered.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun signInWithCredential(idToken: String): User? {
        return authApi.signInWithCredential(idToken)?.toUser()
    }

    override suspend fun createUserWithEmailAndPassword(
        authEmail: String,
        authPassword: String
    ): User? {
       return authApi.createUserWithEmailAndPassword(authEmail, authPassword)?.toUser()
    }

    override suspend fun signInWithEmailAndPassword(
        authEmail: String,
        authPassword: String
    ): User? {
        return authApi.signInWithEmailAndPassword(authEmail, authPassword)?.toUser()
    }
}