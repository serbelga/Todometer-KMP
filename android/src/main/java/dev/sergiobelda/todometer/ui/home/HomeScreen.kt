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

package dev.sergiobelda.todometer.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.R
import dev.sergiobelda.todometer.common.compose.ui.components.HorizontalDivider
import dev.sergiobelda.todometer.common.compose.ui.components.SingleLineItem
import dev.sergiobelda.todometer.common.compose.ui.components.TwoLineItem
import dev.sergiobelda.todometer.common.compose.ui.task.TaskItem
import dev.sergiobelda.todometer.common.compose.ui.tasklist.TaskListItem
import dev.sergiobelda.todometer.common.compose.ui.theme.TodometerColors
import dev.sergiobelda.todometer.common.compose.ui.theme.TodometerTypography
import dev.sergiobelda.todometer.common.compose.ui.theme.drawerShape
import dev.sergiobelda.todometer.common.compose.ui.theme.sheetShape
import dev.sergiobelda.todometer.common.data.doIfError
import dev.sergiobelda.todometer.common.data.doIfSuccess
import dev.sergiobelda.todometer.common.model.Task
import dev.sergiobelda.todometer.common.model.TaskList
import dev.sergiobelda.todometer.common.preferences.AppTheme
import dev.sergiobelda.todometer.preferences.appThemeMap
import dev.sergiobelda.todometer.ui.components.ToDometerAlertDialog
import dev.sergiobelda.todometer.ui.components.ToDometerTitle
import dev.sergiobelda.todometer.ui.components.ToDometerTopAppBar
import dev.sergiobelda.todometer.ui.theme.ToDometerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import java.io.Serializable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    addTaskList: () -> Unit,
    editTaskList: () -> Unit,
    addTask: () -> Unit,
    openTask: (String) -> Unit,
    openSourceLicenses: () -> Unit,
    about: () -> Unit,
    homeViewModel: HomeViewModel = getViewModel()
) {
    val scope = rememberCoroutineScope()
    // TODO: Use skipHalfExpanded when available.
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var currentSheet: HomeBottomSheet by rememberSaveable { mutableStateOf(HomeBottomSheet.MenuBottomSheet) }

    val scaffoldState = rememberScaffoldState()

    var selectedTask by remember { mutableStateOf("") }

    var deleteTaskAlertDialogState by remember { mutableStateOf(false) }
    var deleteTaskListAlertDialogState by remember { mutableStateOf(false) }
    var chooseThemeAlertDialogState by remember { mutableStateOf(false) }

    var taskLists: List<TaskList> by remember { mutableStateOf(emptyList()) }
    val taskListsResultState = homeViewModel.taskLists.collectAsState()
    taskListsResultState.value.doIfSuccess { taskLists = it }

    var taskListSelected: TaskList? by remember { mutableStateOf(null) }
    val taskListSelectedResultState = homeViewModel.taskListSelected.collectAsState()
    taskListSelectedResultState.value.doIfSuccess {
        taskListSelected = it
    }.doIfError {
        taskListSelected = null
    }

    var tasks: List<Task> by remember { mutableStateOf(emptyList()) }
    val tasksResultState = homeViewModel.tasks.collectAsState()
    tasksResultState.value.doIfSuccess { tasks = it }

    val appThemeState = homeViewModel.appTheme.collectAsState()

    val defaultTaskListName = stringResource(R.string.default_task_list_name)

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetElevation = 16.dp,
        sheetShape = sheetShape,
        sheetContent = {
            MoreBottomSheet(
                editTaskListClick = {
                    scope.launch {
                        sheetState.hide()
                        editTaskList()
                    }
                },
                editTaskListEnabled = taskListSelected != null,
                deleteTaskListClick = {
                    deleteTaskListAlertDialogState = true
                },
                deleteTaskListEnabled = taskListSelected != null, // taskLists.size > 1,
                currentTheme = appThemeState.value,
                chooseThemeClick = {
                    chooseThemeAlertDialogState = true
                },
                openSourceLicensesClick = {
                    scope.launch {
                        sheetState.hide()
                        openSourceLicenses()
                    }
                },
                aboutClick = {
                    scope.launch {
                        sheetState.hide()
                        about()
                    }
                }
            )
        }
    ) {
        Scaffold(
            drawerShape = drawerShape,
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
            drawerContent = {
                DrawerContent(
                    taskListSelected?.id ?: "",
                    defaultTaskListName,
                    taskLists,
                    addTaskList,
                    selectTaskList = {
                        homeViewModel.setTaskListSelected(it)
                        scope.launch {
                            delay(100)
                            scaffoldState.drawerState.close()
                        }
                    }
                )
            },
            scaffoldState = scaffoldState,
            topBar = {
                ToDometerTopAppBar(
                    onMenuClick = {
                        scope.launch { scaffoldState.drawerState.open() }
                    },
                    onMoreClick = {
                        scope.launch { sheetState.show() }
                    },
                    taskListSelected?.name ?: defaultTaskListName,
                    tasks
                )
            },
            content = {
                taskListsResultState.value.doIfSuccess { taskLists ->
                    if (deleteTaskAlertDialogState) {
                        DeleteTaskAlertDialog(
                            onDismissRequest = { deleteTaskAlertDialogState = false },
                            deleteTask = { homeViewModel.deleteTask(selectedTask) }
                        )
                    }
                    if (deleteTaskListAlertDialogState) {
                        DeleteTaskListAlertDialog(
                            onDismissRequest = { deleteTaskListAlertDialogState = false },
                            deleteTaskList = {
                                homeViewModel.deleteTaskList()
                                scope.launch {
                                    sheetState.hide()
                                }
                            }
                        )
                    }
                    if (chooseThemeAlertDialogState) {
                        ChooseThemeAlertDialog(
                            currentTheme = appThemeState.value,
                            onDismissRequest = { chooseThemeAlertDialogState = false },
                            chooseTheme = { theme -> homeViewModel.setAppTheme(theme) }
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
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    backgroundColor = TodometerColors.primary,
                    onClick = addTask
                ) {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = stringResource(R.string.add_task)
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End
        )
    }
}

@Composable
fun ChooseThemeAlertDialog(
    currentTheme: AppTheme,
    onDismissRequest: () -> Unit,
    chooseTheme: (theme: AppTheme) -> Unit
) {
    var themeSelected by remember { mutableStateOf(currentTheme) }
    ToDometerAlertDialog(
        title = {
            Text(
                text = stringResource(R.string.choose_theme),
                modifier = Modifier.padding(16.dp)
            )
        },
        onDismissRequest = onDismissRequest,
        body = {
            LazyColumn {
                appThemeMap.forEach { (appTheme, appThemeOption) ->
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = themeSelected == appTheme,
                                    onClick = { themeSelected = appTheme },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                        ) {
                            RadioButton(
                                selected = themeSelected == appTheme,
                                onClick = { themeSelected = appTheme }
                            )
                            Text(
                                text = stringResource(appThemeOption.modeNameRes),
                                style = TodometerTypography.body1,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    chooseTheme(themeSelected)
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
fun DeleteTaskListAlertDialog(onDismissRequest: () -> Unit, deleteTaskList: () -> Unit) {
    AlertDialog(
        title = {
            Text(stringResource(R.string.delete_task_list))
        },
        onDismissRequest = onDismissRequest,
        text = {
            Text(stringResource(R.string.delete_task_list_question))
        },
        confirmButton = {
            TextButton(
                onClick = {
                    deleteTaskList()
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
fun DrawerContent(
    selectedTaskListId: String,
    defaultTaskListName: String,
    taskLists: List<TaskList>,
    addTaskList: () -> Unit,
    selectTaskList: (String) -> Unit
) {
    Column {
        Box(
            modifier = Modifier.height(56.dp).fillMaxWidth()
        ) {
            ToDometerTitle(
                modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp)
            )
        }
        HorizontalDivider()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(56.dp)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.task_lists).uppercase(),
                style = TodometerTypography.overline
            )
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = addTaskList) {
                Text(
                    stringResource(R.string.add_task_list),
                    style = TodometerTypography.caption
                )
            }
        }
        HorizontalDivider()
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            item {
                TaskListItem(defaultTaskListName, selectedTaskListId == "") {
                    selectTaskList("")
                }
            }
            items(taskLists) { taskList ->
                TaskListItem(taskList.name, taskList.id == selectedTaskListId) {
                    selectTaskList(taskList.id)
                }
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
        itemsIndexed(tasks) { index, task ->
            TaskItem(
                task,
                onDoingClick = onDoingClick,
                onDoneClick = onDoneClick,
                onClick = onTaskItemClick,
                onLongClick = onTaskItemLongClick
            )
            if (index == tasks.lastIndex) {
                Spacer(modifier = Modifier.height(80.dp))
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
                painterResource(R.drawable.no_tasks),
                modifier = Modifier.size(240.dp).padding(bottom = 24.dp),
                contentDescription = null
            )
            Text(stringResource(R.string.no_tasks))
        }
    }
}

@Composable
fun EmptyTaskListsView(addTaskList: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center).padding(bottom = 72.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(R.drawable.no_task_lists),
                modifier = Modifier.size(240.dp).padding(bottom = 24.dp),
                contentDescription = null
            )
            Text(
                stringResource(R.string.no_task_lists),
                modifier = Modifier.padding(bottom = 48.dp)
            )
            Button(onClick = addTaskList) {
                Text(text = stringResource(R.string.add_task_list))
            }
        }
    }
}

@Composable
fun MoreBottomSheet(
    editTaskListClick: () -> Unit,
    editTaskListEnabled: Boolean,
    deleteTaskListClick: () -> Unit,
    deleteTaskListEnabled: Boolean,
    chooseThemeClick: () -> Unit,
    openSourceLicensesClick: () -> Unit,
    aboutClick: () -> Unit,
    currentTheme: AppTheme
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 16.dp)
    ) {
        SingleLineItem(
            icon = {
                Icon(
                    Icons.Outlined.Edit,
                    contentDescription = stringResource(R.string.edit_task_list)
                )
            },
            text = {
                Text(
                    stringResource(R.string.edit_task_list),
                    style = TodometerTypography.caption
                )
            },
            onClick = editTaskListClick,
            enabled = editTaskListEnabled
        )
        SingleLineItem(
            icon = {
                Icon(
                    Icons.Outlined.Delete,
                    contentDescription = stringResource(R.string.delete_task_list)
                )
            },
            text = {
                Text(
                    stringResource(R.string.delete_task_list),
                    style = TodometerTypography.caption
                )
            },
            onClick = deleteTaskListClick,
            enabled = deleteTaskListEnabled
        )
        HorizontalDivider()
        TwoLineItem(
            icon = {
                appThemeMap[currentTheme]?.themeIconRes?.let {
                    Icon(
                        painterResource(it),
                        contentDescription = stringResource(R.string.theme)
                    )
                }
            },
            text = {
                Text(
                    stringResource(R.string.theme),
                    style = TodometerTypography.caption
                )
            },
            subtitle = {
                appThemeMap[currentTheme]?.modeNameRes?.let {
                    Text(stringResource(it), style = TodometerTypography.caption)
                }
            },
            onClick = chooseThemeClick
        )
        HorizontalDivider()
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            TextButton(onClick = openSourceLicensesClick) {
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

sealed class HomeBottomSheet : Serializable {
    object MenuBottomSheet : HomeBottomSheet()
    object MoreBottomSheet : HomeBottomSheet()
}

@Preview
@Composable
fun EmptyTasksListPreview() {
    ToDometerTheme {
        EmptyTasksListView()
    }
}
