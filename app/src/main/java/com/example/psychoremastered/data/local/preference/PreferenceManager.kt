package com.example.psychoremastered.data.local.preference

interface PreferenceManager {

    suspend fun putBoolean(key: String, value: Boolean)

    suspend fun getBoolean(key: String): Boolean?
}