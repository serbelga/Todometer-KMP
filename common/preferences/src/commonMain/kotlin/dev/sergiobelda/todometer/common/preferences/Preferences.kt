/*
 * Copyright 2021 Sergio Belda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.sergiobelda.todometer.common.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences as DataStorePreferences

class Preferences(private val dataStore: DataStore<DataStorePreferences>) {

    suspend fun set(key: String, value: String) {
        val stringKey = stringPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[stringKey] = value
        }
    }

    suspend fun set(key: String, value: Long) {
        val longKey = longPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[longKey] = value
        }
    }

    suspend fun set(key: String, value: Int) {
        val intKey = intPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[intKey] = value
        }
    }

    fun getString(key: String): Flow<String?> =
        dataStore.data.map { preferences ->
            val stringKey = stringPreferencesKey(key)
            preferences[stringKey]
        }

    fun getStringOrDefault(key: String, default: String): Flow<String> =
        dataStore.data.map { preferences ->
            val stringKey = stringPreferencesKey(key)
            preferences[stringKey] ?: default
        }

    fun getLong(key: String): Flow<Long?> =
        dataStore.data.map { preferences ->
            val longKey = longPreferencesKey(key)
            preferences[longKey]
        }

    fun getLongOrDefault(key: String, default: Long): Flow<Long> =
        dataStore.data.map { preferences ->
            val longKey = longPreferencesKey(key)
            preferences[longKey] ?: default
        }

    fun getInt(key: String): Flow<Int?> =
        dataStore.data.map { preferences ->
            val intKey = intPreferencesKey(key)
            preferences[intKey]
        }

    fun getIntOrDefault(key: String, default: Int): Flow<Int> =
        dataStore.data.map { preferences ->
            val intKey = intPreferencesKey(key)
            preferences[intKey] ?: default
        }
}
