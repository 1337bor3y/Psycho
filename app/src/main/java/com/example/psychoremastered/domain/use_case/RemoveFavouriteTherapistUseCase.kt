package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.repository.TherapistRepository
import javax.inject.Inject

class RemoveFavouriteTherapistUseCase @Inject constructor(
    private val therapistRepository: TherapistRepository
) {
    suspend operator fun invoke(therapistId: String) =
        therapistRepository.removeFavouriteTherapist(therapistId)
}