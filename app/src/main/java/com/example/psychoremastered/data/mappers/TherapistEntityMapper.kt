package com.example.psychoremastered.data.mappers

import com.example.psychoremastered.data.local.entity.DegreeEntity
import com.example.psychoremastered.data.local.entity.TherapistEntity
import com.example.psychoremastered.domain.model.Degree
import com.example.psychoremastered.domain.model.Therapist

fun Therapist.toTherapistEntity(): TherapistEntity {
    return TherapistEntity(
        therapistId = id,
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
            DegreeEntity(
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

fun TherapistEntity.toTherapist(): Therapist {
    return Therapist(
        id = therapistId,
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