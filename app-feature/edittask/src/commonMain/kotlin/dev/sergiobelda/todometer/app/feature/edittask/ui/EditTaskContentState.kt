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

package dev.sergiobelda.todometer.app.feature.edittask.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import dev.sergiobelda.todometer.common.ui.base.BaseContentState
import dev.sergiobelda.todometer.common.ui.base.BaseEvent

data class EditTaskContentState internal constructor(
    private val taskTitle: String,
    private val taskDescription: String,
) : BaseContentState {

    var titleTextFieldValue: String by mutableStateOf(taskTitle)
        private set

    var descriptionTextFieldValue: String by mutableStateOf(taskDescription)
        private set

    override fun handleEvent(event: BaseEvent) {
    }

    companion object {
        internal fun saver() = mapSaver(
            save = {
                mapOf(
                    TaskTitleKey to it.taskTitle,
                    TaskDescriptionKey to it.taskTitle,
                )
            },
            restore = {
                EditTaskContentState(
                    taskTitle = it[TaskTitleKey] as String,
                    taskDescription = it[TaskDescriptionKey] as String,
                )
            },
        )

        private const val TaskTitleKey: String = "TASK_TITLE"
        private const val TaskDescriptionKey: String = "TASK_DESCRIPTION"
    }
}

@Composable
internal fun rememberEditTaskContentState(
    taskTitle: String,
    taskDescription: String,
): EditTaskContentState = rememberSaveable(
    inputs = arrayOf(taskTitle, taskDescription),
    saver = EditTaskContentState.saver(),
) {
    EditTaskContentState(
        taskTitle = taskTitle,
        taskDescription = taskDescription,
    )
}
