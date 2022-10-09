/*
 * Copyright 2021 Sergio Belda
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

package dev.sergiobelda.todometer.wear.ui.deletetask

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.dialog.Alert
import dev.sergiobelda.todometer.wear.R
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun DeleteTaskScreen(
    taskId: String,
    onDeleteTask: () -> Unit,
    navigateBack: () -> Unit,
    deleteTaskViewModel: DeleteTaskViewModel = getViewModel(parameters = { parametersOf(taskId) })
) {
    Alert(
        icon = { Icon(Icons.Outlined.Delete, null) },
        title = {},
        content = { Text(stringResource(R.string.delete_task_question)) },
        positiveButton = {
            Button(
                onClick = {
                    deleteTaskViewModel.deleteTask()
                    onDeleteTask()
                }
            ) {
                Icon(Icons.Rounded.Check, null)
            }
        },
        negativeButton = {
            Button(colors = ButtonDefaults.secondaryButtonColors(), onClick = navigateBack) {
                Icon(Icons.Rounded.Clear, null)
            }
        }
    )
}
