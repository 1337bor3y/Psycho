package com.example.psychoremastered.data.mappers

import com.example.psychoremastered.data.remote.dto.DegreeDto
import com.example.psychoremastered.data.remote.dto.TherapistDto
import com.example.psychoremastered.domain.model.Degree
import com.example.psychoremastered.domain.model.Therapist

fun Therapist.toTherapistDto(): TherapistDto {
    return TherapistDto(
        id = id,
        avatarUri = avatarUri,
        email = email,
        displayName = displayName,
        specializations = specializations,
        workFields = workFields,
        languages = languages,
        description = description,
        price = price,
        hasDegree = hasDegree,
        degrees = degrees.map {
            DegreeDto(
                id = it.id,
                university = it.university,
                speciality = it.speciality,
                admissionYear = it.admissionYear,
                graduationYear = it.graduationYear,
                documentImage = it.documentImage
            )
        }
    )
}

fun TherapistDto.toTherapist(): Therapist {
    return Therapist(
        id = id,
        avatarUri = avatarUri,
        email = email,
        displayName = displayName,
        specializations = specializations,
        workFields = workFields,
        languages = languages,
        description = description,
        price = price,
        hasDegree = hasDegree,
        degrees = degrees.map {
            Degree(
                id = it.id,
                university = it.university,
                speciality = it.speciality,
                admissionYear = it.admissionYear,
                graduationYear = it.graduationYear,
                documentImage = it.documentImage
            )
        }
    )
}