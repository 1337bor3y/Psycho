package com.example.psychoremastered.presentation.auth

import com.example.psychoremastered.domain.model.User

data class AuthState(
    val user: User? = null,
    val signInError: String? = null
)