package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.model.Therapist
import com.example.psychoremastered.domain.repository.TherapistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTherapistUseCase @Inject constructor(
    private val therapistRepository: TherapistRepository
) {
    operator fun invoke(therapistId: String): Flow<Therapist?> {
        return therapistRepository.getTherapist(therapistId = therapistId)
    }
}