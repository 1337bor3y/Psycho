package com.example.psychoremastered.data.repository

import com.example.psychoremastered.data.mappers.toUnavailableTimeDto
import com.example.psychoremastered.data.remote.UnavailableTimeApi
import com.example.psychoremastered.domain.model.UnavailableTime
import com.example.psychoremastered.domain.repository.UnavailableTimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UnavailableTimeRepositoryImpl @Inject constructor(
    private val unavailableTimeApi: UnavailableTimeApi
): UnavailableTimeRepository {

    override suspend fun saveUnavailableTime(unavailableTime: UnavailableTime) {
        unavailableTimeApi.saveUnavailableTime(unavailableTime.toUnavailableTimeDto())
    }

    override suspend fun removeUnavailableTime(unavailableTime: UnavailableTime) {
        unavailableTimeApi.removeUnavailableTime(unavailableTime.toUnavailableTimeDto())
    }

    override fun getUnavailableTime(therapistId: String, date: String): Flow<List<String>> {
        return unavailableTimeApi.getUnavailableTime(therapistId, date)
    }
}