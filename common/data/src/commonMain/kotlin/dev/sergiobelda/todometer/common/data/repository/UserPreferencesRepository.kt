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

package dev.sergiobelda.todometer.common.data.repository

import dev.sergiobelda.fonament.preferences.FonamentPreferences
import dev.sergiobelda.todometer.common.domain.preference.AppTheme
import dev.sergiobelda.todometer.common.domain.repository.IUserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository for performing data operations on [preferences].
 */
class UserPreferencesRepository(
    private val preferences: FonamentPreferences,
) : IUserPreferencesRepository {
    override fun taskListSelected(): Flow<String> = preferences.getStringOrDefault(TASK_LIST_SELECTED_KEY, "")

    override suspend fun setTaskListSelected(taskListSelectedId: String) {
        preferences[TASK_LIST_SELECTED_KEY] = taskListSelectedId
    }

    override fun getUserTheme(): Flow<AppTheme> =
        preferences.getInt(APP_THEME_KEY).map { theme ->
            theme?.let { enumValues<AppTheme>().getOrNull(it) } ?: AppTheme.FOLLOW_SYSTEM
        }

    override suspend fun setUserTheme(theme: AppTheme) {
        preferences[APP_THEME_KEY] = theme.ordinal
    }

    companion object {
        private const val TASK_LIST_SELECTED_KEY = "task_list_selected"
        private const val APP_THEME_KEY = "app_theme"
    }
}
