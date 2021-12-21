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

package dev.sergiobelda.todometer.common.repository

import dev.sergiobelda.todometer.common.preferences.AppTheme
import kotlinx.coroutines.flow.Flow

interface IUserPreferencesRepository {

    /**
     * Retrieves the current task list selected Id every time it changes in
     * user preferences.
     */
    fun taskListSelected(): Flow<String>

    /**
     * Sets the current task list selected by its id.
     *
     * @param [taskListSelectedId] TaskList id.
     */
    suspend fun setTaskListSelected(taskListSelectedId: String)

    /**
     * Retrieves the current selected [AppTheme] in user preferences
     * every time it changes.
     */
    fun getUserTheme(): Flow<AppTheme>

    /**
     * Updates the current selected [AppTheme].
     */
    suspend fun setUserTheme(theme: AppTheme)
}
