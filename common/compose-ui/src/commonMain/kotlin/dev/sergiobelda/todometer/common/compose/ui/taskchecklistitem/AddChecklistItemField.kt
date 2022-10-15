/*
 * Copyright 2022 Sergio Belda
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

package dev.sergiobelda.todometer.common.compose.ui.taskchecklistitem

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChecklistItemField(
    placeholder: @Composable (() -> Unit)? = null,
    onAddTaskCheckListItem: (String) -> Unit
) {
    var taskChecklistItemText by remember { mutableStateOf("") }
    val addTaskChecklistItemAction = {
        if (taskChecklistItemText.isNotBlank()) {
            onAddTaskCheckListItem(taskChecklistItemText)
            taskChecklistItemText = ""
        }
    }
    Row(modifier = Modifier.fillMaxWidth().padding(start = 16.dp)) {
        OutlinedTextField(
            value = taskChecklistItemText,
            onValueChange = { taskChecklistItemText = it },
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent
            ),
            placeholder = placeholder,
            maxLines = 1,
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = { addTaskChecklistItemAction() }
            ),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
        )
        if (taskChecklistItemText.isNotBlank()) {
            IconButton(
                onClick = addTaskChecklistItemAction
            ) {
                Icon(
                    Icons.Rounded.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
