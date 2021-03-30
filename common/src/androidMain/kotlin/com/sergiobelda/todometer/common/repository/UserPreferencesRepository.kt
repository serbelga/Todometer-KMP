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

package com.sergiobelda.todometer.common.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sergiobelda.todometer.common.repository.UserPreferencesRepository.Companion.DATA_STORE_NAME
import com.sergiobelda.todometer.common.repository.UserPreferencesRepository.PreferencesKeys.PROJECT_SELECTED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

lateinit var appContext: Context

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

actual class UserPreferencesRepository actual constructor() {

    actual fun projectSelected(): Flow<Long> = appContext.dataStore.data.map { preferences ->
        preferences[PROJECT_SELECTED] ?: 1
    }

    actual suspend fun setProjectSelected(projectSelected: Long) {
        appContext.dataStore.edit { preferences ->
            preferences[PROJECT_SELECTED] = projectSelected
        }
    }

    private object PreferencesKeys {
        val PROJECT_SELECTED = longPreferencesKey(PROJECT_SELECTED_KEY)
    }

    companion object {
        const val DATA_STORE_NAME = "preferences"
        private const val PROJECT_SELECTED_KEY = "project_selected"
    }
}
