package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.model.Resource
import com.example.psychoremastered.domain.repository.ValidationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ValidateProfileImageUseCase @Inject constructor(
    private val validationRepository: ValidationRepository
) {
    operator fun invoke(profileImage: String): Flow<Resource<Boolean>> {
        return validationRepository.validateProfileImage(profileImage)
    }
}