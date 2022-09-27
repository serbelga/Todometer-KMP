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

package dev.sergiobelda.todometer.common.domain.usecase.apptheme

import dev.sergiobelda.todometer.common.domain.preference.AppTheme
import dev.sergiobelda.todometer.common.domain.repository.IUserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetAppThemeUseCase(private val userPreferencesRepository: IUserPreferencesRepository) {

    /**
     * Retrieves the current selected [AppTheme] in user preferences
     * every time it changes.
     *
     * @return A Flow that emits the current theme selected.
     */
    operator fun invoke(): Flow<AppTheme> = userPreferencesRepository.getUserTheme()
}
