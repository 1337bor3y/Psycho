package com.example.psychoremastered.domain.repository

import com.example.psychoremastered.domain.model.UnavailableTime
import kotlinx.coroutines.flow.Flow

interface UnavailableTimeRepository {

    suspend fun saveUnavailableTime(unavailableTime: UnavailableTime)

    suspend fun removeUnavailableTime(unavailableTime: UnavailableTime)

    fun getUnavailableTime(therapistId: String, date: String): Flow<List<String>>
}