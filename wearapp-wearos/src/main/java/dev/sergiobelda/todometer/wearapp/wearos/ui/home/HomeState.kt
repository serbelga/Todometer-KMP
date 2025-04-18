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
import dev.sergiobelda.todometer.common.domain.model.TaskList
import dev.sergiobelda.todometer.common.ui.error.ErrorUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class HomeState(
    val isLoading: Boolean = false,
    val taskLists: ImmutableList<TaskList> = persistentListOf(),
    val errorUi: ErrorUi? = null,
)
