package com.example.psychoremastered.data.repository

import com.example.psychoremastered.data.local.TherapistDao
import com.example.psychoremastered.data.mappers.toTherapistEntity
import com.example.psychoremastered.domain.model.Therapist
import com.example.psychoremastered.domain.repository.TherapistRepository
import javax.inject.Inject

class TherapistRepositoryImpl @Inject constructor(
    private val therapistDao: TherapistDao
) : TherapistRepository {

    override suspend fun upsertTherapist(therapist: Therapist) {
        therapistDao.upsertTherapist(therapist.toTherapistEntity())
    }
}