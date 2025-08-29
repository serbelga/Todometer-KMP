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

package dev.sergiobelda.todometer.app.feature.edittask.ui

import androidx.compose.runtime.Immutable
import dev.sergiobelda.fonament.ui.FonamentUIState
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.ui.error.ErrorUi

@Immutable
data class EditTaskUIState(
    val isLoading: Boolean = false,
    val task: Task? = null,
    val errorUi: ErrorUi? = null,
) : FonamentUIState
