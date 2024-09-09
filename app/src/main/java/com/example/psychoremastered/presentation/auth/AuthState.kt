package com.example.psychoremastered.presentation.auth

import com.example.psychoremastered.domain.model.User

data class AuthState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val signInError: String? = null,
    val email: String = "",
    val firstName: String = "",
    val surname: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val profileImage: String = "",
)