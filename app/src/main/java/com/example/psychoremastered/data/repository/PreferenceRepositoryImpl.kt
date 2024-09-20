package com.example.psychoremastered.data.repository

import com.example.psychoremastered.data.local.preference.PreferenceManager
import com.example.psychoremastered.domain.repository.PreferenceRepository
import javax.inject.Inject

class PreferenceRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager
) : PreferenceRepository {

    override suspend fun putString(key: String, value: String) {
        preferenceManager.putString(
            key = key,
            value = value
        )
    }

    override suspend fun getString(key: String): String? {
       return preferenceManager.getString(key)
    }
}