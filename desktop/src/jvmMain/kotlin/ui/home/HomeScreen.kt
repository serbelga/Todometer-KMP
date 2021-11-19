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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.twotone.Settings
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.components.ProjectProgress
import dev.sergiobelda.todometer.common.compose.ui.project.ProjectListItem
import dev.sergiobelda.todometer.common.compose.ui.task.TaskItem
import dev.sergiobelda.todometer.common.compose.ui.theme.TodometerColors
import dev.sergiobelda.todometer.common.compose.ui.theme.TodometerTypography
import dev.sergiobelda.todometer.common.data.doIfSuccess
import dev.sergiobelda.todometer.common.model.Project
import dev.sergiobelda.todometer.common.model.Tag
import dev.sergiobelda.todometer.common.model.Task
import dev.sergiobelda.todometer.common.usecase.GetProjectSelectedTasksUseCase
import dev.sergiobelda.todometer.common.usecase.GetProjectSelectedUseCase
import dev.sergiobelda.todometer.common.usecase.GetProjectsUseCase
import dev.sergiobelda.todometer.common.usecase.InsertProjectUseCase
import dev.sergiobelda.todometer.common.usecase.InsertTaskProjectSelectedUseCase
import dev.sergiobelda.todometer.common.usecase.SetProjectSelectedUseCase
import dev.sergiobelda.todometer.common.usecase.SetTaskDoingUseCase
import dev.sergiobelda.todometer.common.usecase.SetTaskDoneUseCase
import koin
import kotlinx.coroutines.launch
import ui.icons.iconToDometer

@Composable
fun HomeScreen() {
    var addProjectAlertDialogState by remember { mutableStateOf(false) }
    var addTaskAlertDialogState by remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val setTaskDoingUseCase = koin.get<SetTaskDoingUseCase>()
    val setTaskDoneUseCase = koin.get<SetTaskDoneUseCase>()
    val getProjectSelectedUseCase = koin.get<GetProjectSelectedUseCase>()
    val setProjectSelectedUseCase = koin.get<SetProjectSelectedUseCase>()
    val getProjectSelectedTasksUseCase = koin.get<GetProjectSelectedTasksUseCase>()
    val getProjectsUseCase = koin.get<GetProjectsUseCase>()
    val insertProjectUseCase = koin.get<InsertProjectUseCase>()
    val insertTaskProjectSelectedUseCase = koin.get<InsertTaskProjectSelectedUseCase>()

    var projectSelected: Project? by remember { mutableStateOf(null) }
    val projectResultState by getProjectSelectedUseCase().collectAsState(null)
    projectResultState?.doIfSuccess { projectSelected = it }

    var tasks: List<Task> by remember { mutableStateOf(emptyList()) }
    val tasksResultState by getProjectSelectedTasksUseCase().collectAsState(null)
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
                backgroundColor = TodometerColors.surface,
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.TwoTone.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            if (projects.isNotEmpty()) {
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
            }
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
                ) { title, description, _ ->
                    coroutineScope.launch {
                        insertTaskProjectSelectedUseCase.invoke(title, description, Tag.GRAY)
                    }
                }
            }
        }
        if (projects.isNotEmpty()) {
            Row {
                ProjectsNavigationDrawer(
                    projects,
                    projectSelected,
                    onProjectClick = {
                        coroutineScope.launch {
                            setProjectSelectedUseCase.invoke(it)
                        }
                    },
                    onAddProjectClick = { addProjectAlertDialogState = true }
                )
                Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                Column {
                    ProjectProgress(projectSelected, tasks)
                    if (tasks.isEmpty()) {
                        EmptyTasksListView()
                    } else {
                        TasksListView(
                            tasks,
                            onDoingClick = {
                                coroutineScope.launch {
                                    setTaskDoingUseCase(it)
                                }
                            },
                            onDoneClick = {
                                coroutineScope.launch {
                                    setTaskDoneUseCase(it)
                                }
                            },
                            onTaskItemClick = {},
                            onTaskItemLongClick = {}
                        )
                    }
                }
            }
        } else {
            EmptyProjectsView(addProject = { addProjectAlertDialogState = true })
        }
    }
}

@Composable
fun EmptyProjectsView(addProject: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center).padding(bottom = 72.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource("images/no_projects.svg"),
                modifier = Modifier.size(240.dp).padding(bottom = 24.dp),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text("There's any project", modifier = Modifier.padding(bottom = 48.dp))
            Button(onClick = addProject) {
                Text("Add project")
            }
        }
    }
}

@Composable
fun TasksListView(
    tasks: List<Task>,
    onDoingClick: (String) -> Unit,
    onDoneClick: (String) -> Unit,
    onTaskItemClick: (String) -> Unit,
    onTaskItemLongClick: (String) -> Unit
) {
    LazyColumn {
        items(tasks) {
            TaskItem(
                task = it,
                onDoingClick = onDoingClick,
                onDoneClick = onDoneClick,
                onClick = onTaskItemClick,
                onLongClick = onTaskItemLongClick
            )
        }
    }
}

@Composable
fun EmptyTasksListView() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center).padding(bottom = 72.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource("images/no_tasks.svg"),
                modifier = Modifier.size(240.dp).padding(bottom = 24.dp),
                contentDescription = null
            )
            Text("There are any task")
        }
    }
}

@Composable
fun ProjectsNavigationDrawer(
    projects: List<Project>,
    projectSelected: Project?,
    onProjectClick: (projectId: String) -> Unit,
    onAddProjectClick: () -> Unit
) {
    Column(modifier = Modifier.requiredWidth(280.dp).padding(top = 16.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "PROJECTS",
                style = TodometerTypography.overline
            )
            OutlinedButton(
                onClick = onAddProjectClick,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Icon(Icons.Rounded.Add, contentDescription = "Add project")
                Text(text = "Add project")
            }
        }
        LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
            items(projects) { project ->
                ProjectListItem(
                    project,
                    project.id == projectSelected?.id
                ) {
                    onProjectClick(it)
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
