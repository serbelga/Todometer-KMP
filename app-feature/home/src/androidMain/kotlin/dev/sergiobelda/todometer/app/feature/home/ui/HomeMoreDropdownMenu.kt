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

package dev.sergiobelda.todometer.app.feature.home.ui

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.common.designsystem.resources.images.Images
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Delete
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Edit
import dev.sergiobelda.todometer.common.resources.TodometerResources

@Composable
internal actual fun HomeMoreDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onEditTaskListClick: () -> Unit,
    onDeleteTaskListClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuItem(
            onClick = onEditTaskListClick,
            leadingIcon = { Icon(Images.Icons.Edit, contentDescription = null) },
            text = {
                Text(
                    TodometerResources.strings.editTaskList,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        )
        DropdownMenuItem(
            onClick = onDeleteTaskListClick,
            leadingIcon = {
                Icon(
                    Images.Icons.Delete,
                    contentDescription = null
                )
            },
            text = {
                Text(
                    TodometerResources.strings.deleteTaskList,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        )
    }
}
