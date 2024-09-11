package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.model.Resource
import com.example.psychoremastered.domain.repository.ValidationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ValidateConfirmPasswordUseCase @Inject constructor(
    private val validationRepository: ValidationRepository
) {
    operator fun invoke(password: String, confirmPassword: String) : Flow<Resource<Boolean>> {
       return validationRepository.validateConfirmPassword(password, confirmPassword)
    }
}