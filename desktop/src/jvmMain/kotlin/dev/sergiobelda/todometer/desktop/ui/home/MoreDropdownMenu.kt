/*
 * Copyright 2023 Sergio Belda
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

package dev.sergiobelda.todometer.desktop.ui.home

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.compose.stringResource
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.HorizontalDivider
import dev.sergiobelda.todometer.common.resources.MR

// TODO: Replace by material3 implementation when available
@Composable
fun MoreDropdownMenu(
    editTaskListEnabled: Boolean,
    editTaskListClick: () -> Unit,
    aboutClick: () -> Unit,
    expanded: Boolean,
    onDismissRequest: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        // TODO: Add icons and supportingText when use DropdownMenuItem from material3
        DropdownMenuItem(
            enabled = editTaskListEnabled,
            onClick = editTaskListClick
        ) {
            Text(stringResource(MR.strings.edit_task_list))
        }
        HorizontalDivider()
        DropdownMenuItem(
            onClick = aboutClick
        ) {
            Text(stringResource(MR.strings.about))
        }
    }
}
