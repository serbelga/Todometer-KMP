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

package dev.sergiobelda.todometer.feature.home

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.common.designsystem.resources.TodometerIcons
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.stringResource

@Composable
fun DeleteTaskListAlertDialog(onDismissRequest: () -> Unit, onDeleteTaskListClick: () -> Unit) {
    AlertDialog(
        icon = {
            Icon(TodometerIcons.Warning, contentDescription = null)
        },
        title = {
            Text(stringResource(MR.strings.delete_task_list))
        },
        onDismissRequest = onDismissRequest,
        text = {
            Text(stringResource(MR.strings.delete_task_list_question))
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDeleteTaskListClick()
                    onDismissRequest()
                }
            ) {
                Text(stringResource(MR.strings.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(MR.strings.cancel))
            }
        }
    )
}
