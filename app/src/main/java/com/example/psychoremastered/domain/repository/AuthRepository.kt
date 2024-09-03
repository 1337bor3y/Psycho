package com.example.psychoremastered.domain.repository

import com.example.psychoremastered.domain.model.User

interface AuthRepository {

    suspend fun signInWithCredential(idToken: String): User?
}