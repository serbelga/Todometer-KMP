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

package com.sergiobelda.todometer.common.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sergiobelda.todometer.common.preferences.Preferences.Companion.DATA_STORE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

lateinit var appContext: Context

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

actual class Preferences {

    actual suspend fun set(key: String, value: String) {
        val stringKey = stringPreferencesKey(key)
        appContext.dataStore.edit { preferences ->
            preferences[stringKey] = value
        }
    }

    actual suspend fun set(key: String, value: Long) {
        val longKey = longPreferencesKey(key)
        appContext.dataStore.edit { preferences ->
            preferences[longKey] = value
        }
    }

    actual fun getString(key: String, default: String): Flow<String> =
        appContext.dataStore.data.map { preferences ->
            val stringKey = stringPreferencesKey(key)
            preferences[stringKey] ?: default
        }

    actual fun getStringOrNull(key: String): Flow<String?> =
        appContext.dataStore.data.map { preferences ->
            val stringKey = stringPreferencesKey(key)
            preferences[stringKey]
        }

    actual fun getLong(
        key: String,
        default: Long
    ): Flow<Long> = appContext.dataStore.data.map { preferences ->
        val longKey = longPreferencesKey(key)
        preferences[longKey] ?: default
    }

    actual fun getLongOrNull(key: String): Flow<Long?> =
        appContext.dataStore.data.map { preferences ->
            val longKey = longPreferencesKey(key)
            preferences[longKey]
        }

    companion object {
        const val DATA_STORE_NAME = "preferences"
    }
}
