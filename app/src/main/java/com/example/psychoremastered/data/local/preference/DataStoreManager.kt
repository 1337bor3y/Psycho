package com.example.psychoremastered.data.local.preference

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.psychoremastered.core.util.Constants
import kotlinx.coroutines.flow.first

private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = Constants.PREFERENCE_NAME)

class DataStoreManager(
    private val application: Application
) : PreferenceManager {

    override suspend fun putBoolean(key: String, value: Boolean) {
        val dataStoreKey = booleanPreferencesKey(key)
        application.dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    override suspend fun getBoolean(key: String): Boolean? {
        val dataStoreKey = booleanPreferencesKey(key)
        val preferences = application.dataStore.data.first()
        return preferences[dataStoreKey]
    }
}