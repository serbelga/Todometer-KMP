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

import dev.sergiobelda.fonament.presentation.ui.FonamentEvent
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.ui.extensions.TextFieldValueChangeEvent

sealed interface EditTaskEvent : FonamentEvent {
    data class UpdateTask(
        val title: String,
        val tag: Tag,
        val description: String? = null,
        val dueDate: Long? = null,
    ) : EditTaskEvent

    data class TitleValueChange(
        override val value: String,
    ) : EditTaskEvent,
        TextFieldValueChangeEvent

    data class DescriptionValueChange(
        override val value: String,
    ) : EditTaskEvent,
        TextFieldValueChangeEvent

    data object ConfirmDatePickerDialog : EditTaskEvent

    data object DismissDatePickerDialog : EditTaskEvent

    data object ShowDatePickerDialog : EditTaskEvent

    data object ConfirmTimePickerDialog : EditTaskEvent

    data object DismissTimePickerDialog : EditTaskEvent

    data object ShowTimePickerDialog : EditTaskEvent

    data object ClearDateTime : EditTaskEvent

    data class SelectTag(
        val tag: Tag,
    ) : EditTaskEvent
}
