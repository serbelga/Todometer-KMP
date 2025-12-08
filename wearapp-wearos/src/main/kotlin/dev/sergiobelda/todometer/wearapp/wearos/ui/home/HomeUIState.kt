/*
 * Copyright 2022 Sergio Belda
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

package dev.sergiobelda.todometer.wearapp.wearos.ui.home

import androidx.compose.runtime.Immutable
import dev.sergiobelda.fonament.presentation.ui.FonamentUIState
import dev.sergiobelda.todometer.common.domain.model.TaskList
import dev.sergiobelda.todometer.common.ui.error.ErrorUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed interface HomeUIState : FonamentUIState {
    data object Loading : HomeUIState

    @Immutable
    data class Success(
        val taskLists: ImmutableList<TaskList> = persistentListOf(),
    ) : HomeUIState

    data class Error(
        val errorUi: ErrorUi?,
    ) : HomeUIState
}
