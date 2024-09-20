package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.repository.PreferenceRepository
import javax.inject.Inject

class PutStringPreferenceUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke(key: String, value: String) {
        preferenceRepository.putString(
            key = key,
            value = value
        )
    }
}