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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.icons.iconToDometer
import dev.sergiobelda.todometer.common.compose.ui.project.ProjectListItem
import dev.sergiobelda.todometer.common.compose.ui.task.TaskItem
import dev.sergiobelda.todometer.common.compose.ui.theme.TodometerColors
import dev.sergiobelda.todometer.common.compose.ui.theme.TodometerTypography
import dev.sergiobelda.todometer.common.data.doIfSuccess
import dev.sergiobelda.todometer.common.model.Project
import dev.sergiobelda.todometer.common.model.Task
import dev.sergiobelda.todometer.common.usecase.GetProjectSelectedUseCase
import dev.sergiobelda.todometer.common.usecase.GetProjectsUseCase
import dev.sergiobelda.todometer.common.usecase.GetTasksUseCase
import dev.sergiobelda.todometer.common.usecase.InsertProjectUseCase
import dev.sergiobelda.todometer.common.usecase.SetProjectSelectedUseCase
import dev.sergiobelda.todometer.common.usecase.SetTaskDoingUseCase
import dev.sergiobelda.todometer.common.usecase.SetTaskDoneUseCase
import koin
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    var addProjectAlertDialogState by remember { mutableStateOf(false) }
    var addTaskAlertDialogState by remember { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()

    val setTaskDoingUseCase = koin.get<SetTaskDoingUseCase>()
    val setTaskDoneUseCase = koin.get<SetTaskDoneUseCase>()
    val getProjectSelectedUseCase = koin.get<GetProjectSelectedUseCase>()
    val setProjectSelectedUseCase = koin.get<SetProjectSelectedUseCase>()
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

    var projectSelected: Project? by remember { mutableStateOf(null) }
    val projectResultState by getProjectSelectedUseCase().collectAsState(null)
    projectResultState?.doIfSuccess { projectSelected = it }

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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = iconToDometer(),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp).padding(end = 8.dp)
                        )
                        Text(text = "ToDometer")
                    }
                },
                elevation = 0.dp,
                backgroundColor = TodometerColors.surface
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
                onClick = { addTaskAlertDialogState = true },
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
                    onDismissRequest = { addProjectAlertDialogState = false }
                ) { projectName ->
                    coroutineScope.launch {
                        insertProjectUseCase.invoke(projectName)
                    }
                }
            }
            if (addTaskAlertDialogState) {
                AddTaskAlertDialog(
                    onDismissRequest = { addTaskAlertDialogState = false }
                ) { _, _, _ ->
                }
            }
        }

        Row {
            Column(modifier = Modifier.requiredWidth(256.dp).padding(top = 16.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Projects",
                        style = TodometerTypography.subtitle2
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                LazyColumn {
                    items(projects) { project ->
                        ProjectListItem(
                            project,
                            project.id == projectSelected?.id
                        ) {
                            coroutineScope.launch {
                                setProjectSelectedUseCase.invoke(it)
                            }
                        }
                    }
                }
                TextButton(
                    onClick = { addProjectAlertDialogState = true },
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp).fillMaxWidth()
                ) {
                    Icon(Icons.Rounded.Add, contentDescription = "Add project")
                    Text(text = "Add project")
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

@Composable
fun DrawerContainer(addProject: () -> Unit) {
    OutlinedButton(onClick = addProject) {
        Icon(Icons.Rounded.Add, contentDescription = "Add project")
        Text(text = "Add project")
    }
}
