package com.example.psychoremastered.data.remote

import com.example.psychoremastered.data.remote.dto.UnavailableTimeDto
import kotlinx.coroutines.flow.Flow

interface UnavailableTimeApi {

    suspend fun saveUnavailableTime(unavailableTime: UnavailableTimeDto)

    suspend fun removeUnavailableTime(unavailableTime: UnavailableTimeDto)

    fun getUnavailableTime(therapistId: String, date: String): Flow<List<String>>
}