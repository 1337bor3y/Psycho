package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.model.User
import com.example.psychoremastered.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithCredential @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): User? {
        return authRepository.signInWithCredential(idToken)
    }
}