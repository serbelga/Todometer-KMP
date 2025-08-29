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

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import dev.sergiobelda.fonament.ui.FonamentContentState
import dev.sergiobelda.fonament.ui.FonamentEvent
import dev.sergiobelda.todometer.app.common.ui.extensions.selectedTimeMillis
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.ui.extensions.localTime

@OptIn(ExperimentalMaterial3Api::class)
data class EditTaskContentState internal constructor(
    private val initialTitle: String,
    private val initialDescription: String?,
    private val initialTag: Tag,
    private val initialDueDate: Long?,
    internal val datePickerState: DatePickerState?,
    internal val timePickerState: TimePickerState?,
) : FonamentContentState {

    var title: String by mutableStateOf(initialTitle)
        private set

    var description: String by mutableStateOf(initialDescription.orEmpty())
        private set

    var tag by mutableStateOf(initialTag)
        private set

    var dueDate: Long? by mutableStateOf(initialDueDate)

    val isSaveButtonEnabled: Boolean by derivedStateOf { title.isNotBlank() }

    var datePickerDialogVisible by mutableStateOf(false)
        private set

    var timePickerDialogVisible by mutableStateOf(false)
        private set

    override fun handleEvent(event: FonamentEvent) {
        when (event) {
            is EditTaskEvent.TitleValueChange -> titleValueChange(event)
            is EditTaskEvent.DescriptionValueChange -> descriptionValueChange(event)
            is EditTaskEvent.ConfirmDatePickerDialog -> confirmDatePickerDialog()
            is EditTaskEvent.DismissDatePickerDialog -> dismissDatePickerDialog()
            is EditTaskEvent.ShowDatePickerDialog -> showDatePickerDialog()
            is EditTaskEvent.ConfirmTimePickerDialog -> confirmTimePickerDialog()
            is EditTaskEvent.DismissTimePickerDialog -> dismissTimePickerDialog()
            is EditTaskEvent.ShowTimePickerDialog -> showTimePickerDialog()
            is EditTaskEvent.ClearDateTime -> clearDateTime()
            is EditTaskEvent.SelectTag -> selectTag(event)
        }
    }

    private fun titleValueChange(event: EditTaskEvent.TitleValueChange) {
        title = event.value
    }

    private fun descriptionValueChange(event: EditTaskEvent.DescriptionValueChange) {
        description = event.value
    }

    private fun confirmDatePickerDialog() {
        datePickerDialogVisible = false
        updateTaskDueDate()
    }

    private fun dismissDatePickerDialog() {
        datePickerDialogVisible = false
    }

    private fun showDatePickerDialog() {
        datePickerDialogVisible = true
    }

    private fun confirmTimePickerDialog() {
        timePickerDialogVisible = false
        updateTaskDueDate()
    }

    private fun dismissTimePickerDialog() {
        timePickerDialogVisible = false
    }

    private fun showTimePickerDialog() {
        timePickerDialogVisible = true
    }

    private fun clearDateTime() {
        dueDate = null
    }

    private fun selectTag(event: EditTaskEvent.SelectTag) {
        tag = event.tag
    }

    private fun updateTaskDueDate() {
        dueDate =
            datePickerState?.selectedDateMillis?.plus(timePickerState?.selectedTimeMillis ?: 0)
    }

    companion object {
        internal fun saver(
            datePickerState: DatePickerState?,
            timePickerState: TimePickerState?,
        ) = mapSaver(
            save = {
                mapOf(
                    TitleKey to it.title,
                    DescriptionKey to it.description,
                    TagKey to it.tag,
                    DueDateKey to it.dueDate,
                )
            },
            restore = {
                EditTaskContentState(
                    initialTitle = it[TitleKey] as String,
                    initialDescription = it[DescriptionKey] as String,
                    initialTag = it[TagKey] as Tag,
                    initialDueDate = it[DueDateKey] as? Long,
                    datePickerState = datePickerState,
                    timePickerState = timePickerState,
                )
            },
        )

        private const val TitleKey: String = "TITLE"

        private const val DescriptionKey: String = "DESCRIPTION"

        private const val TagKey: String = "TAG"

        private const val DueDateKey: String = "DUE_DATE"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun rememberEditTaskContentState(
    task: Task?,
): EditTaskContentState {
    val datePickerState = task?.let { rememberDatePickerState(it.dueDate) }
    val timePickerState = task?.let {
        val localTime = it.dueDate?.localTime()
        rememberTimePickerState(
            initialHour = localTime?.hour ?: 0,
            initialMinute = localTime?.minute ?: 0,
        )
    }
    return rememberSaveable(
        inputs = arrayOf(task),
        saver = EditTaskContentState.saver(
            datePickerState = datePickerState,
            timePickerState = timePickerState,
        ),
    ) {
        EditTaskContentState(
            initialTitle = task?.title.orEmpty(),
            initialDescription = task?.description,
            initialTag = task?.tag ?: Tag.UNSPECIFIED,
            initialDueDate = task?.dueDate,
            datePickerState = datePickerState,
            timePickerState = timePickerState,
        )
    }
}
