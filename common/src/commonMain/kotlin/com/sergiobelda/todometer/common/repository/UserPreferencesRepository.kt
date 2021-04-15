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

import com.sergiobelda.todometer.common.preferences.Preferences
import kotlinx.coroutines.flow.Flow

class UserPreferencesRepository(private val preferences: Preferences) : IUserPreferencesRepository {

    override fun projectSelected(): Flow<Long> = preferences.getLong(PROJECT_SELECTED_KEY)

    override suspend fun setProjectSelected(projectSelected: Long) {
        preferences.set(PROJECT_SELECTED_KEY, projectSelected)
    }

    companion object {
        private const val PROJECT_SELECTED_KEY = "project_selected"
    }
}
