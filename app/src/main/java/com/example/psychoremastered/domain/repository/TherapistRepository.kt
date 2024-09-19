package com.example.psychoremastered.domain.repository

import com.example.psychoremastered.domain.model.Therapist
import kotlinx.coroutines.flow.Flow

interface TherapistRepository {

    suspend fun saveTherapist(therapist: Therapist): Boolean

    suspend fun removeTherapist(therapist: Therapist): Boolean

    fun getTherapist(therapistId: String): Flow<Therapist>

    fun getAllTherapists(): Flow<List<Therapist>>
}