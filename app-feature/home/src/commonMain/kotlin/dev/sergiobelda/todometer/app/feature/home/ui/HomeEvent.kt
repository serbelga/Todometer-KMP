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

package dev.sergiobelda.todometer.app.feature.home.ui

import dev.sergiobelda.fonament.presentation.ui.FonamentEvent

sealed interface HomeEvent : FonamentEvent {
    data class SetTaskDoing(
        val id: String,
    ) : HomeEvent

    data class SetTaskDone(
        val id: String,
    ) : HomeEvent

    data class DeleteTask(
        val id: String,
    ) : HomeEvent

    data object DeleteSelectedTasks : HomeEvent

    data object DeleteTaskList : HomeEvent

    data class SetTaskListSelected(
        val id: String,
    ) : HomeEvent

    data class ToggleSelectTask(
        val id: String,
    ) : HomeEvent

    data object ClearSelectedTasks : HomeEvent

    data object ToggleSelectedTasksPinnedValue : HomeEvent

    data object OpenDrawer : HomeEvent

    data class CloseDrawer(
        val onClose: () -> Unit = {},
    ) : HomeEvent

    data object ShowHomeMoreDropdown : HomeEvent

    data object DismissHomeMoreDropdown : HomeEvent

    data object ShowDeleteTaskListAlertDialog : HomeEvent

    data object DismissDeleteTaskListAlertDialog : HomeEvent

    data class ShowDeleteTaskAlertDialog(
        val id: String,
    ) : HomeEvent

    data object DismissDeleteTaskAlertDialog : HomeEvent

    data object ShowDeleteTasksAlertDialog : HomeEvent

    data object DismissDeleteTasksAlertDialog : HomeEvent
}
