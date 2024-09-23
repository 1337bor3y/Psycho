package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.core.util.Constants
import com.example.psychoremastered.domain.repository.PreferenceRepository
import javax.inject.Inject

class GetIsClientPreferenceUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke(key: String = Constants.PREFERENCE_KEY_IS_CLIENT): Boolean? {
        return preferenceRepository.getBoolean(key)
    }
}