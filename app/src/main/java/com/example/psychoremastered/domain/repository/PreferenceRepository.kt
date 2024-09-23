package com.example.psychoremastered.domain.repository

interface PreferenceRepository {

    suspend fun putBoolean(key: String, value: Boolean)

    suspend fun getBoolean(key: String): Boolean?
}