package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.repository.UnavailableTimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUnavailableTimeUseCase @Inject constructor(
    private val unavailableTimeRepository: UnavailableTimeRepository
) {
    operator fun invoke(therapistId: String, date: String): Flow<List<String>> {
        return unavailableTimeRepository.getUnavailableTime(therapistId, date)
    }
}