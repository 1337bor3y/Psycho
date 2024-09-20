package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.repository.PreferenceRepository
import javax.inject.Inject

class GetStringPreferenceUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke(key: String): String? {
        return preferenceRepository.getString(key)
    }
}