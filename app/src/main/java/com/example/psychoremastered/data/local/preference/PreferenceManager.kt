package com.example.psychoremastered.data.local.preference

interface PreferenceManager {

    suspend fun putString(key: String, value: String)

    suspend fun getString(key: String): String?
}