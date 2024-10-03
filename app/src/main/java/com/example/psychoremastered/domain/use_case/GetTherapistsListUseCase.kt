package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.model.Resource
import com.example.psychoremastered.domain.model.Therapist
import com.example.psychoremastered.domain.repository.TherapistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTherapistsListUseCase @Inject constructor(
    private val therapistRepository: TherapistRepository
) {
    operator fun invoke(): Flow<Resource<List<Therapist>>> = flow {
        emit(Resource.Loading<List<Therapist>>())
        val therapists = therapistRepository.getAllTherapists().first()
        if (therapists.isNotEmpty()) {
            emit(Resource.Success<List<Therapist>>(therapists))
        } else {
            emit(Resource.Error<List<Therapist>>("There are no therapists!"))
        }
    }
}