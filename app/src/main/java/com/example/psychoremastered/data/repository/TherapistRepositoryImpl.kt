package com.example.psychoremastered.data.repository

import com.example.psychoremastered.data.mappers.toTherapist
import com.example.psychoremastered.data.mappers.toTherapistDto
import com.example.psychoremastered.data.remote.TherapistApi
import com.example.psychoremastered.domain.model.Therapist
import com.example.psychoremastered.domain.repository.TherapistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TherapistRepositoryImpl @Inject constructor(
    private val therapistApi: TherapistApi
) : TherapistRepository {

    override suspend fun saveTherapist(therapist: Therapist): Boolean {
        return therapistApi.saveTherapist(therapist.toTherapistDto())
    }

    override suspend fun removeTherapist(therapist: Therapist): Boolean {
        return therapistApi.removeTherapist(therapist.toTherapistDto())
    }

    override fun getTherapist(therapistId: String): Flow<Therapist> {
        return therapistApi.getTherapist(therapistId).map {
            it.toTherapist()
        }
    }

    override fun getAllTherapists(): Flow<List<Therapist>> {
        return therapistApi.getAllTherapists().map { therapist ->
            therapist.map {
                it.toTherapist()
            }
        }
    }
}