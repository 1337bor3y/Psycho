package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.model.UnavailableTime
import com.example.psychoremastered.domain.repository.UnavailableTimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveUnavailableTimeUseCase @Inject constructor(
    private val unavailableTimeRepository: UnavailableTimeRepository
) {
    suspend operator fun invoke(unavailableTime: UnavailableTime) {
         unavailableTimeRepository.saveUnavailableTime(unavailableTime)
    }
}