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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.R
import com.sergiobelda.todometer.model.Project
import com.sergiobelda.todometer.model.Task
import com.sergiobelda.todometer.model.TaskState
import com.sergiobelda.todometer.ui.components.HorizontalDivider
import com.sergiobelda.todometer.ui.theme.MaterialColors
import com.sergiobelda.todometer.viewmodel.MainViewModel
import java.util.Locale

@Composable
fun AddTaskScreen(
    mainViewModel: MainViewModel,
    navigateUp: () -> Unit
) {
    var taskTitle by savedInstanceState { "" }
    var taskDescription by savedInstanceState { "" }
    val radioOptions = mainViewModel.projectList
    val (selectedProject, onProjectSelected) = remember { mutableStateOf(radioOptions[0]) }
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
                title = { Text(stringResource(id = R.string.add_task)) },
                actions = {
                    Button(
                        onClick = {
                            mainViewModel.insertTask(
                                Task(
                                    title = taskTitle,
                                    description = taskDescription,
                                    state = TaskState.DOING,
                                    projectId = selectedProject.id,
                                    tagId = null
                                )
                            )
                            navigateUp()
                        }
                    ) {
                        Text(stringResource(id = R.string.save))
                    }
                }
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
                    ).fillMaxWidth()
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
                    onImeActionPerformed = { _, softwareKeyboardController -> softwareKeyboardController?.hideSoftwareKeyboard() }
                )
                ProjectRadioGroup(radioOptions, selectedProject, onProjectSelected)
            }
        }
    )
}

@Composable
fun ProjectRadioGroup(
    projectList: List<Project>,
    selectedProject: Project,
    onProjectSelected: (project: Project) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.project).toUpperCase(Locale.ROOT),
            style = MaterialTheme.typography.overline,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
        )
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
        LazyColumnFor(
            items = projectList,
            modifier = Modifier.height(140.dp)
        ) { project ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .preferredHeight(56.dp)
                    .selectable(
                        selected = (project == selectedProject),
                        onClick = { onProjectSelected(project) }
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (project == selectedProject),
                    onClick = { onProjectSelected(project) }
                )
                Text(
                    text = project.name,
                    style = MaterialTheme.typography.body1.merge(),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
        HorizontalDivider()
    }
}
