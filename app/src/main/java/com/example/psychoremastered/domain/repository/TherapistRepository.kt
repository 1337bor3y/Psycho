package com.example.psychoremastered.domain.repository

import com.example.psychoremastered.domain.model.Therapist
import kotlinx.coroutines.flow.Flow

interface TherapistRepository {

    suspend fun saveTherapist(therapist: Therapist)

    suspend fun removeTherapist(therapist: Therapist)

    fun getTherapist(therapistId: String): Flow<Therapist?>

    fun getAllTherapists(): Flow<List<Therapist>>
}