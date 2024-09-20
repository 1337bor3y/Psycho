package com.example.psychoremastered.data.mappers

import com.example.psychoremastered.data.auth.model.AuthUser
import com.example.psychoremastered.domain.model.User

fun AuthUser.toUser(): User {
    return User(
        userId = userId,
        isNewUser = isNewUser,
        email = email,
        displayName = displayName,
        profilePictureUri = profilePictureUri
    )
}