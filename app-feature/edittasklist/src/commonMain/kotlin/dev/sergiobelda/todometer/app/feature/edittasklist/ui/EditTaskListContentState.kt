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

package dev.sergiobelda.todometer.app.feature.edittasklist.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import dev.sergiobelda.fonament.ui.FonamentContentState
import dev.sergiobelda.fonament.ui.FonamentEvent

data class EditTaskListContentState internal constructor(
    private val taskListName: String,
) : FonamentContentState {

    var nameTextFieldValue: String by mutableStateOf(taskListName)
        private set

    override fun handleEvent(event: FonamentEvent) {
        when (event) {
            is EditTaskListEvent.NameTextFieldValueChange -> {
                nameTextFieldValue = event.value
            }
        }
    }

    companion object {
        internal fun saver(): Saver<EditTaskListContentState, String> = Saver(
            save = {
                it.nameTextFieldValue
            },
            restore = {
                EditTaskListContentState(
                    taskListName = it,
                )
            },
        )
    }
}

@Composable
internal fun rememberEditTaskListContentState(
    taskListName: String,
): EditTaskListContentState = rememberSaveable(
    inputs = arrayOf(taskListName),
    saver = EditTaskListContentState.saver(),
) {
    EditTaskListContentState(
        taskListName = taskListName,
    )
}
