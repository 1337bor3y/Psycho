package com.example.psychoremastered.data.remote

import com.example.psychoremastered.data.remote.dto.TherapistDto
import kotlinx.coroutines.flow.Flow

interface TherapistApi {

    suspend fun saveTherapist(therapist: TherapistDto): Boolean

    suspend fun removeTherapist(therapist: TherapistDto): Boolean

    fun getTherapist(therapistId: String): Flow<TherapistDto?>

    fun getAllTherapists(): Flow<List<TherapistDto>>
}