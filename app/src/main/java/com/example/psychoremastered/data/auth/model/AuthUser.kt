package com.example.psychoremastered.data.auth.model

data class AuthUser(
    val userId: String,
    val isNewUser: Boolean,
    val email: String?,
    val displayName: String?,
    val profilePictureUri: String?
)
