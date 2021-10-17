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

package ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.common.data.doIfSuccess
import com.sergiobelda.todometer.common.model.Project
import com.sergiobelda.todometer.common.model.Task
import com.sergiobelda.todometer.common.usecase.GetProjectSelectedUseCase
import com.sergiobelda.todometer.common.usecase.GetProjectsUseCase
import com.sergiobelda.todometer.common.usecase.GetTasksUseCase
import com.sergiobelda.todometer.common.usecase.InsertProjectUseCase
import com.sergiobelda.todometer.common.usecase.SetTaskDoingUseCase
import com.sergiobelda.todometer.common.usecase.SetTaskDoneUseCase
import com.sergiobelda.todometer.compose.ui.components.TitledTextField
import com.sergiobelda.todometer.compose.ui.task.TaskItem
import com.sergiobelda.todometer.compose.ui.theme.TodometerColors
import koin
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(addTask: () -> Unit) {
    var addProjectAlertDialogState by remember { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val setTaskDoingUseCase = koin.get<SetTaskDoingUseCase>()
    val setTaskDoneUseCase = koin.get<SetTaskDoneUseCase>()
    val getProjectSelectedUseCase = koin.get<GetProjectSelectedUseCase>()
    val getTasksUseCase = koin.get<GetTasksUseCase>()
    val getProjectsUseCase = koin.get<GetProjectsUseCase>()
    val insertProjectUseCase = koin.get<InsertProjectUseCase>()

    val coroutineScope = rememberCoroutineScope()
    val setTaskDoing: (String) -> Unit = {
        coroutineScope.launch {
            setTaskDoingUseCase(it)
        }
    }
    val setTaskDone: (String) -> Unit = {
        coroutineScope.launch {
            setTaskDoneUseCase(it)
        }
    }

    var project: Project? by remember { mutableStateOf(null) }
    val projectResultState by getProjectSelectedUseCase().collectAsState(null)
    projectResultState?.doIfSuccess { project = it }

    var tasks: List<Task> by remember { mutableStateOf(emptyList()) }
    val tasksResultState by getTasksUseCase().collectAsState(null)
    tasksResultState?.doIfSuccess { tasks = it }

    var projects: List<Project> by remember { mutableStateOf(emptyList()) }
    val projectsResultState by getProjectsUseCase().collectAsState(null)
    projectsResultState?.doIfSuccess { projects = it }

    Scaffold(
        /*
        drawerShape = RoundedCornerShape(0),
        drawerContent = {
            DrawerContainer(addProject = { addProjectAlertDialogState = true })
        },
        */
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "ToDometer")
                },
                elevation = 0.dp,
                navigationIcon = {
                    /*
                    IconButton(
                        onClick = { scope.launch { scaffoldState.drawerState.open() } }
                    ) {
                        Icon(Icons.Rounded.Menu, contentDescription = "Menu")
                    }
                    */
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                icon = {
                    Icon(Icons.Rounded.Add, "Add task")
                },
                text = {
                    Text("Add task")
                },
                onClick = addTask,
                backgroundColor = TodometerColors.primary
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        scaffoldState = scaffoldState
    ) {
        Divider()
        Column(modifier = Modifier.fillMaxSize()) {
            if (addProjectAlertDialogState) {
                AddProjectAlertDialog(
                    addProject = { projectName ->
                        coroutineScope.launch {
                            insertProjectUseCase.invoke(projectName)
                        }
                    },
                    onDismissRequest = { addProjectAlertDialogState = false }
                )
            }
        }

        Row {
            Column(modifier = Modifier.requiredWidth(256.dp).padding(top = 16.dp)) {
                OutlinedButton(
                    onClick = { addProjectAlertDialogState = true },
                    modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp)
                ) {
                    Icon(Icons.Rounded.Add, contentDescription = "Add project")
                    Text(text = "Add project")
                }
                LazyColumn {
                    items(projects) {
                        Text(text = it.name)
                    }
                }
            }
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            LazyColumn {
                items(tasks) {
                    TaskItem(
                        task = it,
                        onDoingClick = setTaskDoing,
                        onDoneClick = setTaskDone,
                        {},
                        {}
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddProjectAlertDialog(
    addProject: (name: String) -> Unit,
    onDismissRequest: () -> Unit
) {
    var projectName by rememberSaveable { mutableStateOf("") }
    var projectNameInputError by remember { mutableStateOf(false) }

    AlertDialog(
        title = {
            Text(text = "Add project")
        },
        onDismissRequest = onDismissRequest,
        text = {
            Column {
                TitledTextField(
                    title = "Name",
                    value = projectName,
                    onValueChange = {
                        projectName = it
                        projectNameInputError = false
                    },
                    placeholder = { Text(text = "Enter project name") },
                    singleLine = true,
                    isError = projectNameInputError,
                    errorMessage = "Field must not be empty",
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    )
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (projectName.isBlank()) {
                        projectNameInputError = true
                    } else {
                        addProject(projectName)
                        onDismissRequest()
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancelar")
            }
        },
        modifier = Modifier.requiredWidth(480.dp)
    )
}

@Composable
fun DrawerContainer(addProject: () -> Unit) {
    OutlinedButton(onClick = addProject) {
        Icon(Icons.Rounded.Add, contentDescription = "Add project")
        Text(text = "Add project")
    }
}
