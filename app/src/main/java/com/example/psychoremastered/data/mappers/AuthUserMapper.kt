package com.example.psychoremastered.data.mappers

import com.example.psychoremastered.data.auth.model.AuthUser
import com.example.psychoremastered.data.remote.dto.ClientDto
import com.example.psychoremastered.data.remote.dto.DegreeDto
import com.example.psychoremastered.data.remote.dto.TherapistDto
import com.example.psychoremastered.domain.model.Client
import com.example.psychoremastered.domain.model.Degree
import com.example.psychoremastered.domain.model.Therapist
import com.example.psychoremastered.domain.model.User

fun AuthUser.toUser(): User {
    return User(
        userId = userId,
        email = email,
        displayName = displayName,
        profilePictureUri = profilePictureUri
    )
}