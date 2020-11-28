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

package com.sergiobelda.todometer.ui.task

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.R
import com.sergiobelda.todometer.model.Task
import com.sergiobelda.todometer.ui.components.ProjectSelector
import com.sergiobelda.todometer.ui.theme.MaterialColors
import com.sergiobelda.todometer.viewmodel.MainViewModel

@Composable
fun EditTaskScreen(
    taskId: Int,
    mainViewModel: MainViewModel,
    navigateUp: () -> Unit
) {
    val taskState = mainViewModel.getTask(taskId).observeAsState()
    taskState.value?.let { task ->
        var taskTitle by savedInstanceState { task.title }
        var taskDescription by savedInstanceState { task.description }
        val radioOptions = mainViewModel.projectList
        val projectIndex = radioOptions.indexOfFirst { it.id == task.projectId }.takeUnless { it == -1 } ?: 0
        val (selectedProject, onProjectSelected) = remember { mutableStateOf(radioOptions[projectIndex]) }
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialColors.surface,
                    contentColor = contentColorFor(MaterialColors.surface),
                    elevation = 0.dp,
                    navigationIcon = {
                        IconButton(onClick = navigateUp) {
                            Icon(Icons.Rounded.ArrowBack)
                        }
                    },
                    title = { Text(stringResource(id = R.string.add_task)) }
                )
            },
            bodyContent = {
                Column {
                    OutlinedTextField(
                        value = taskTitle,
                        onValueChange = { taskTitle = it },
                        label = { Text(stringResource(id = R.string.title)) },
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp,
                            bottom = 8.dp
                        ).fillMaxWidth(),
                        imeAction = ImeAction.Next
                    )
                    OutlinedTextField(
                        value = taskDescription,
                        onValueChange = { taskDescription = it },
                        label = { Text(stringResource(id = R.string.description)) },
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp,
                            bottom = 8.dp
                        ).fillMaxWidth(),
                        imeAction = ImeAction.Done,
                        onImeActionPerformed = { _, softwareKeyboardController -> softwareKeyboardController?.hideSoftwareKeyboard() }
                    )
                    ProjectSelector(radioOptions, selectedProject, onProjectSelected)
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        mainViewModel.updateTask(
                            Task(
                                id = task.id,
                                title = taskTitle,
                                description = taskDescription,
                                state = task.state,
                                projectId = selectedProject.id,
                                tagId = task.tagId
                            )
                        )
                        navigateUp()
                    },
                    icon = { Icon(Icons.Rounded.Check) }
                )
            }
        )
    }
}
