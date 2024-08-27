package com.example.psychoremastered.data.mappers

import com.example.psychoremastered.data.local.model.DegreeEntity
import com.example.psychoremastered.data.local.model.TherapistEntity
import com.example.psychoremastered.domain.model.Therapist

fun Therapist.toTherapistEntity(): TherapistEntity {
    return TherapistEntity(
        id = id,
        specializations = specializations,
        workFields = workFields,
        languages = languages,
        description = description,
        price = price,
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