/*
 * Copyright 2020 Sergio Belda
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

package com.sergiobelda.todometer.ui.addtask

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.android.R
import com.sergiobelda.todometer.common.data.doIfSuccess
import com.sergiobelda.todometer.common.model.Tag
import com.sergiobelda.todometer.compose.ui.theme.TodometerColors
import com.sergiobelda.todometer.ui.components.TextField
import org.koin.androidx.compose.getViewModel

@Composable
fun AddTaskScreen(
    navigateUp: () -> Unit,
    addTaskViewModel: AddTaskViewModel = getViewModel()
) {
    var taskTitle by rememberSaveable { mutableStateOf("") }
    var taskTitleInputError by remember { mutableStateOf(false) }
    var taskDescription by rememberSaveable { mutableStateOf("") }
    val result = addTaskViewModel.result.observeAsState()
    result.value?.doIfSuccess {
        navigateUp()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = TodometerColors.surface,
                contentColor = contentColorFor(TodometerColors.surface),
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        addTaskViewModel.insertTask(taskTitle, taskDescription, Tag.AMBER)
                    }) {
                        Icon(
                            Icons.Rounded.Check,
                            contentDescription = "Save",
                            tint = TodometerColors.primary
                        )
                    }
                },
                title = { Text(stringResource(id = R.string.add_task)) }
            )
        },
        content = {
            Column {
                TextField(
                    value = taskTitle,
                    onValueChange = {
                        taskTitle = it
                        taskTitleInputError = false
                    },
                    label = { Text(stringResource(id = R.string.title)) },
                    isError = taskTitleInputError,
                    errorMessage = stringResource(id = R.string.field_not_empty),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = taskDescription,
                    onValueChange = { taskDescription = it },
                    label = { Text(stringResource(id = R.string.description)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}
