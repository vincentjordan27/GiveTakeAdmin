package com.vincent.givetakeadmin.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>){

    private val ACCESS_KEY = stringPreferencesKey("access_key")

    fun getUserAccessKey() : Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[ACCESS_KEY] ?: ""
        }
    }

    suspend fun saveUserAccessKey(key: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_KEY] = key
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE : UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>) : UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}