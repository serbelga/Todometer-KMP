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

package com.sergiobelda.todometer.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Brightness4
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.android.R
import com.sergiobelda.todometer.common.data.doIfSuccess
import com.sergiobelda.todometer.common.model.Project
import com.sergiobelda.todometer.common.model.Task
import com.sergiobelda.todometer.compose.ui.components.DragIndicator
import com.sergiobelda.todometer.compose.ui.components.HorizontalDivider
import com.sergiobelda.todometer.compose.ui.components.SingleLineItem
import com.sergiobelda.todometer.compose.ui.components.TwoLineItem
import com.sergiobelda.todometer.compose.ui.task.TaskItem
import com.sergiobelda.todometer.compose.ui.theme.TodometerColors
import com.sergiobelda.todometer.compose.ui.theme.TodometerTypography
import com.sergiobelda.todometer.compose.ui.theme.primarySelected
import com.sergiobelda.todometer.ui.components.ToDometerTopAppBar
import com.sergiobelda.todometer.ui.theme.ToDometerTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    addProject: () -> Unit,
    editProject: () -> Unit,
    addTask: () -> Unit,
    openTask: (String) -> Unit,
    about: () -> Unit,
    homeViewModel: HomeViewModel = getViewModel()
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var currentSheet: HomeBottomSheet by remember { mutableStateOf(HomeBottomSheet.MenuBottomSheet) }

    var selectedTask by remember { mutableStateOf("") }

    var deleteTaskAlertDialogState by remember { mutableStateOf(false) }
    var deleteProjectAlertDialogState by remember { mutableStateOf(false) }
    var chooseThemeAlertDialogState by remember { mutableStateOf(false) }

    var projects: List<Project> by remember { mutableStateOf(emptyList()) }
    val projectsResultState = homeViewModel.projects.collectAsState()
    projectsResultState.value.doIfSuccess { projects = it }

    var projectSelected: Project? by remember { mutableStateOf(null) }
    val projectSelectedResultState = homeViewModel.projectSelected.collectAsState()
    projectSelectedResultState.value.doIfSuccess { projectSelected = it }

    var tasks: List<Task> by remember { mutableStateOf(emptyList()) }
    val tasksResultState = homeViewModel.tasks.collectAsState()
    tasksResultState.value.doIfSuccess { tasks = it }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetElevation = 16.dp,
        sheetContent = {
            when (currentSheet) {
                is HomeBottomSheet.MenuBottomSheet -> {
                    MenuBottomSheet(
                        projectSelected?.id,
                        projects,
                        addProject,
                        selectProject = {
                            homeViewModel.setProjectSelected(it)
                        }
                    )
                }
                is HomeBottomSheet.MoreBottomSheet -> {
                    MoreBottomSheet(
                        editProjectClick = editProject,
                        deleteProjectClick = {
                            deleteProjectAlertDialogState = true
                        },
                        chooseThemeClick = {
                            chooseThemeAlertDialogState = true
                        },
                        aboutClick = {
                            scope.launch {
                                sheetState.hide()
                                about()
                            }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                ToDometerTopAppBar(projectSelected, tasks)
            },
            bottomBar = {
                if (projects.isNotEmpty()) {
                    BottomAppBar(
                        backgroundColor = TodometerColors.surface,
                        contentColor = contentColorFor(TodometerColors.surface),
                        cutoutShape = CircleShape
                    ) {
                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                            IconButton(
                                onClick = {
                                    currentSheet = HomeBottomSheet.MenuBottomSheet
                                    scope.launch { sheetState.show() }
                                }
                            ) {
                                Icon(Icons.Rounded.Menu, contentDescription = "Menu")
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(
                                onClick = {
                                    currentSheet = HomeBottomSheet.MoreBottomSheet
                                    scope.launch { sheetState.show() }
                                }
                            ) {
                                Icon(Icons.Rounded.MoreVert, contentDescription = "More")
                            }
                        }
                    }
                }
            },
            content = {
                if (deleteTaskAlertDialogState) {
                    DeleteTaskAlertDialog(
                        onDismissRequest = { deleteTaskAlertDialogState = false },
                        deleteTask = { homeViewModel.deleteTask(selectedTask) }
                    )
                }
                if (deleteProjectAlertDialogState) {
                    DeleteProjectAlertDialog(
                        onDismissRequest = { deleteProjectAlertDialogState = false },
                        deleteProject = { }
                    )
                }
                if (chooseThemeAlertDialogState) {
                    ChooseThemeAlertDialog(
                        onDismissRequest = { chooseThemeAlertDialogState = false },
                        chooseTheme = { }
                    )
                }
                if (tasks.isEmpty()) {
                    EmptyTasksListView()
                } else {
                    TasksListView(
                        tasks,
                        onDoingClick = {
                            homeViewModel.setTaskDoing(it)
                        },
                        onDoneClick = {
                            homeViewModel.setTaskDone(it)
                        },
                        onTaskItemClick = openTask,
                        onTaskItemLongClick = {
                            deleteTaskAlertDialogState = true
                            selectedTask = it
                        }
                    )
                }
            },
            floatingActionButton = {
                if (!projects.isNullOrEmpty()) {
                    FloatingActionButton(
                        backgroundColor = TodometerColors.primary,
                        onClick = addTask
                    ) {
                        Icon(
                            Icons.Rounded.Add,
                            contentDescription = stringResource(R.string.add_task)
                        )
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true
        )
    }
}

@Composable
fun ChooseThemeAlertDialog(onDismissRequest: () -> Unit, chooseTheme: () -> Unit) {
    AlertDialog(
        title = {},
        onDismissRequest = onDismissRequest,
        text = {
            Column {
            }
        },
        confirmButton = {
            TextButton(onClick = chooseTheme) {
                Text(stringResource(android.R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
fun DeleteProjectAlertDialog(onDismissRequest: () -> Unit, deleteProject: () -> Unit) {
    AlertDialog(
        title = {
            Text(stringResource(R.string.delete_project))
        },
        onDismissRequest = onDismissRequest,
        text = {
            Text(stringResource(R.string.delete_project_question))
        },
        confirmButton = {
            TextButton(onClick = deleteProject) {
                Text(stringResource(android.R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
fun DeleteTaskAlertDialog(onDismissRequest: () -> Unit, deleteTask: () -> Unit) {
    AlertDialog(
        title = {
            Text(stringResource(R.string.delete_task))
        },
        onDismissRequest = onDismissRequest,
        text = {
            Text(stringResource(R.string.delete_task_question))
        },
        confirmButton = {
            TextButton(
                onClick = {
                    deleteTask()
                    onDismissRequest()
                }
            ) {
                Text(stringResource(android.R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
fun MenuBottomSheet(
    selectedProjectId: String?,
    projectList: List<Project>,
    addProject: () -> Unit,
    selectProject: (String) -> Unit
) {
    Column(modifier = Modifier.height(480.dp)) {
        DragIndicator()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(56.dp)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.projects).uppercase(),
                style = TodometerTypography.overline
            )
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(onClick = addProject) {
                Icon(Icons.Rounded.Add, contentDescription = stringResource(R.string.add_project))
                Text(text = stringResource(R.string.add_project))
            }
        }
        HorizontalDivider()
        LazyColumn {
            items(projectList) { project ->
                ProjectListItem(project, project.id == selectedProjectId, selectProject)
            }
        }
    }
}

@Composable
fun ProjectListItem(
    project: Project,
    selected: Boolean,
    onItemClick: (String) -> Unit
) {
    val background = if (selected) {
        Modifier.background(TodometerColors.primarySelected)
    } else {
        Modifier
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(56.dp).clickable(onClick = { onItemClick(project.id) })
            .then(background)
    ) {
        val selectedColor =
            if (selected) TodometerColors.primary else TodometerColors.onSurface.copy(alpha = ContentAlpha.medium)
        Text(
            text = project.name,
            color = selectedColor,
            style = TodometerTypography.subtitle2,
            modifier = Modifier.weight(1f).padding(start = 16.dp, end = 16.dp)
        )
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
        itemsIndexed(tasks) { index, task ->
            TaskItem(
                task,
                onDoingClick = onDoingClick,
                onDoneClick = onDoneClick,
                onClick = onTaskItemClick,
                onLongClick = onTaskItemLongClick
            )
            if (index == tasks.lastIndex) {
                Spacer(modifier = Modifier.height(56.dp))
            }
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
                painterResource(R.drawable.project_completed),
                modifier = Modifier.size(240.dp),
                contentDescription = null
            )
            Text(stringResource(R.string.no_tasks))
        }
    }
}

@Composable
fun MoreBottomSheet(
    editProjectClick: () -> Unit,
    deleteProjectClick: () -> Unit,
    chooseThemeClick: () -> Unit,
    aboutClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 16.dp)
    ) {
        SingleLineItem(
            icon = {
                Icon(
                    Icons.Outlined.Edit,
                    contentDescription = stringResource(R.string.edit_project)
                )
            },
            text = {
                Text(
                    stringResource(R.string.edit_project),
                    style = TodometerTypography.caption
                )
            },
            onClick = editProjectClick
        )
        SingleLineItem(
            icon = {
                Icon(
                    Icons.Outlined.Delete,
                    contentDescription = stringResource(R.string.delete_project)
                )
            },
            text = {
                Text(
                    stringResource(R.string.delete_project),
                    style = TodometerTypography.caption
                )
            },
            onClick = deleteProjectClick
        )
        HorizontalDivider()
        TwoLineItem(
            icon = {
                Icon(
                    Icons.Outlined.Brightness4,
                    contentDescription = stringResource(R.string.theme)
                )
            },
            text = {
                Text(
                    stringResource(R.string.theme),
                    style = TodometerTypography.caption
                )
            },
            subtitle = {
                Text(
                    stringResource(R.string.follow_system),
                    style = TodometerTypography.caption
                )
            },
            onClick = chooseThemeClick
        )
        HorizontalDivider()
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            TextButton(onClick = {}) {
                Text(
                    stringResource(R.string.open_source_licenses),
                    style = TodometerTypography.caption
                )
            }
            Text("·")
            TextButton(onClick = aboutClick) {
                Text(stringResource(R.string.about), style = TodometerTypography.caption)
            }
        }
    }
}

sealed class HomeBottomSheet {
    object MenuBottomSheet : HomeBottomSheet()
    object MoreBottomSheet : HomeBottomSheet()
}

@Preview
@Composable
fun EmptyProjectTaskListPreview() {
    ToDometerTheme {
        EmptyTasksListView()
    }
}
