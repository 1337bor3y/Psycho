package com.example.psychoremastered.data.mappers

import com.example.psychoremastered.data.remote.dto.ClientDto
import com.example.psychoremastered.data.remote.dto.DegreeDto
import com.example.psychoremastered.data.remote.dto.TherapistDto
import com.example.psychoremastered.domain.model.Client
import com.example.psychoremastered.domain.model.Degree
import com.example.psychoremastered.domain.model.Therapist

fun Client.toClientDto(): ClientDto {
    return ClientDto(
        id = id,
        avatarUri = avatarUri,
        email = email,
        displayName = displayName,
    )
}

fun ClientDto.toClient(): Client {
    return Client(
        id = id,
        avatarUri = avatarUri,
        email = email,
        displayName = displayName
    )
}