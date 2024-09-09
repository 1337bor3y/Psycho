package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.model.Resource
import com.example.psychoremastered.domain.model.User
import com.example.psychoremastered.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInWithEmailAndPassword @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(authEmail: String, authPassword: String): Flow<Resource<User>> =
        flow {
            try {
                emit(Resource.Loading<User>())
                val user = authRepository.signInWithEmailAndPassword(authEmail, authPassword)
                user?.let {
                    emit(Resource.Success<User>(it))
                }
            } catch (e: Exception) {
                emit(Resource.Error<User>(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
}