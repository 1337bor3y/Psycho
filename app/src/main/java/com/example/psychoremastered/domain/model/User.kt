package com.example.psychoremastered.domain.model

data class User(
    val userId: String,
    val isNewUser: Boolean,
    val email: String?,
    val displayName: String?,
    val profilePictureUri: String?
)