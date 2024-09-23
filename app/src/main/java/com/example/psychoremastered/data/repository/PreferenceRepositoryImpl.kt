package com.example.psychoremastered.data.repository

import com.example.psychoremastered.data.local.preference.PreferenceManager
import com.example.psychoremastered.domain.repository.PreferenceRepository
import javax.inject.Inject

class PreferenceRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager
) : PreferenceRepository {

    override suspend fun putBoolean(key: String, value: Boolean) {
        preferenceManager.putBoolean(
            key = key,
            value = value
        )
    }

    override suspend fun getBoolean(key: String): Boolean? {
       return preferenceManager.getBoolean(key)
    }
}