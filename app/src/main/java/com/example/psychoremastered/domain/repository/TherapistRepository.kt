package com.example.psychoremastered.domain.repository

import com.example.psychoremastered.domain.model.Therapist

interface TherapistRepository {

    suspend fun upsertTherapist(therapist: Therapist)
}