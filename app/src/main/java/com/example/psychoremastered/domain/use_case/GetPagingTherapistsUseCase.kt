package com.example.psychoremastered.domain.use_case

import androidx.paging.PagingData
import com.example.psychoremastered.domain.model.Therapist
import com.example.psychoremastered.domain.repository.TherapistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagingTherapistsUseCase @Inject constructor(
    private val therapistRepository: TherapistRepository
) {
    operator fun invoke(): Flow<PagingData<Therapist>> {
        return therapistRepository.getAllTherapists()
    }
}