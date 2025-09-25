/*
 * Copyright 2025 Sergio Belda
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

package dev.sergiobelda.todometer.app.feature.taskdetails.ui

import dev.sergiobelda.fonament.presentation.ui.FonamentEvent
import dev.sergiobelda.todometer.common.domain.model.TaskChecklistItemState

sealed interface TaskDetailsEvent : FonamentEvent {

    data class AddTaskChecklistItem(
        internal val text: String,
    ) : TaskDetailsEvent

    data class ClickTaskChecklistItem(
        internal val id: String,
        internal val state: TaskChecklistItemState,
    ) : TaskDetailsEvent

    data class DeleteTaskChecklistItem(
        internal val id: String,
    ) : TaskDetailsEvent

    data object ToggleTaskPinnedValue : TaskDetailsEvent
}
