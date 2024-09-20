package com.example.psychoremastered.domain.repository

interface PreferenceRepository {

    suspend fun putString(key: String, value: String)

    suspend fun getString(key: String): String?
}