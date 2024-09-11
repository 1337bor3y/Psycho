package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.model.Resource
import com.example.psychoremastered.domain.repository.ValidationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(
    private val validationRepository: ValidationRepository
) {
    operator fun invoke(email: String) : Flow<Resource<Boolean>> {
       return validationRepository.validateEmail(email)
    }
}