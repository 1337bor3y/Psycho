package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.repository.TherapistRepository
import javax.inject.Inject

class GetFavouriteTherapistsUseCase @Inject constructor(
    private val therapistRepository: TherapistRepository
) {
    operator fun invoke() =
        therapistRepository.getFavouriteTherapists()
}