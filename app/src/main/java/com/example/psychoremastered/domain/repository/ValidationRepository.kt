package com.example.psychoremastered.domain.repository

import com.example.psychoremastered.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface ValidationRepository {

    fun validateProfileImage(profileImage: String): Flow<Resource<Boolean>>

    fun validateFirstName(firstName: String): Flow<Resource<Boolean>>

    fun validateSurname(surname: String): Flow<Resource<Boolean>>

    fun validateEmail(email: String): Flow<Resource<Boolean>>

    fun validatePassword(password: String): Flow<Resource<Boolean>>

    fun validateConfirmPassword(password: String, confirmPassword: String): Flow<Resource<Boolean>>
}