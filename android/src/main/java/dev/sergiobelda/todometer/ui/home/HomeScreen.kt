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

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberModalBottomSheetState
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
import androidx.compose.ui.draw.clip
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
import dev.sergiobelda.todometer.common.compose.ui.theme.onSurfaceMediumEmphasis
import dev.sergiobelda.todometer.common.compose.ui.theme.outline
import dev.sergiobelda.todometer.common.compose.ui.theme.sheetShape
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskList
import dev.sergiobelda.todometer.common.preferences.AppTheme
import dev.sergiobelda.todometer.preferences.appThemeMap
import dev.sergiobelda.todometer.ui.components.ToDometerAlertDialog
import dev.sergiobelda.todometer.ui.components.ToDometerContentLoadingProgress
import dev.sergiobelda.todometer.ui.components.ToDometerTitle
import dev.sergiobelda.todometer.ui.components.ToDometerTopAppBar
import dev.sergiobelda.todometer.ui.theme.ToDometerTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

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

    val scaffoldState = rememberScaffoldState()
    val closeDrawer: suspend () -> Unit = {
        scaffoldState.drawerState.close()
    }

    var selectedTask by remember { mutableStateOf("") }
    var deleteTaskAlertDialogState by remember { mutableStateOf(false) }
    var deleteTaskListAlertDialogState by remember { mutableStateOf(false) }
    var chooseThemeAlertDialogState by remember { mutableStateOf(false) }

    val taskListSelectedUiState = homeViewModel.taskListSelectedUiState
    val tasksUiState = homeViewModel.tasksUiState
    val taskListsUiState = homeViewModel.taskListsUiState
    val appTheme by homeViewModel.appTheme.collectAsState()

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
                editTaskListEnabled = !taskListSelectedUiState.isDefaultTaskListSelected,
                deleteTaskListClick = {
                    deleteTaskListAlertDialogState = true
                },
                deleteTaskListEnabled = !taskListSelectedUiState.isDefaultTaskListSelected,
                currentTheme = appTheme,
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
                    taskListSelectedUiState.taskListSelected?.id ?: "",
                    defaultTaskListName,
                    taskListsUiState.taskLists,
                    addTaskList = {
                        scope.launch {
                            closeDrawer()
                        }
                        addTaskList()
                    },
                    selectTaskList = {
                        homeViewModel.setTaskListSelected(it)
                        scope.launch { closeDrawer() }
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
                    taskListSelectedUiState.taskListSelected?.name ?: defaultTaskListName,
                    tasksUiState.tasks
                )
            },
            content = {
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
                        currentTheme = appTheme,
                        onDismissRequest = { chooseThemeAlertDialogState = false },
                        chooseTheme = { theme -> homeViewModel.setAppTheme(theme) }
                    )
                }
                if (tasksUiState.isLoading) {
                    ToDometerContentLoadingProgress()
                } else {
                    if (tasksUiState.tasks.isEmpty()) {
                        EmptyTasksListView()
                    } else {
                        TasksListView(
                            tasksUiState.tasks,
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
                            },
                            onSwipeToDismiss = {
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

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class,
    ExperimentalAnimationGraphicsApi::class
)
@Composable
fun TasksListView(
    tasks: List<Task>,
    onDoingClick: (String) -> Unit,
    onDoneClick: (String) -> Unit,
    onTaskItemClick: (String) -> Unit,
    onTaskItemLongClick: (String) -> Unit,
    onSwipeToDismiss: (String) -> Unit
) {
    LazyColumn {
        items(tasks, key = { it.id }) { task ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToEnd) {
                        onSwipeToDismiss(task.id)
                    }
                    it != DismissValue.DismissedToEnd
                }
            )
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.StartToEnd),
                dismissThresholds = {
                    FractionalThreshold(0.1f)
                },
                background = {
                    val color by animateColorAsState(
                        if (dismissState.targetValue == DismissValue.Default) TodometerColors.outline else TodometerColors.error,
                        animationSpec = tween(
                            durationMillis = 400,
                            easing = FastOutSlowInEasing
                        )
                    )
                    val tint by animateColorAsState(
                        if (dismissState.targetValue == DismissValue.Default) TodometerColors.onSurfaceMediumEmphasis else TodometerColors.onError,
                        animationSpec = tween(
                            durationMillis = 400,
                            easing = FastOutSlowInEasing
                        )
                    )
                    val icon = AnimatedImageVector.animatedVectorResource(R.drawable.avd_delete)
                    Box(
                        Modifier.fillMaxSize().background(color).padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Icon(
                            rememberAnimatedVectorPainter(
                                icon,
                                atEnd = dismissState.targetValue == DismissValue.DismissedToEnd
                            ),
                            contentDescription = stringResource(R.string.delete_task),
                            tint = tint
                        )
                    }
                },
                dismissContent = {
                    val dp by animateDpAsState(
                        if (dismissState.targetValue == DismissValue.Default) 0.dp else 8.dp,
                        animationSpec = tween(
                            durationMillis = 400,
                            easing = FastOutSlowInEasing
                        )
                    )
                    TaskItem(
                        task,
                        onDoingClick = onDoingClick,
                        onDoneClick = onDoneClick,
                        onClick = onTaskItemClick,
                        onLongClick = onTaskItemLongClick,
                        modifier = Modifier.clip(RoundedCornerShape(dp))
                    )
                },
                modifier = Modifier.animateItemPlacement()
            )
        }
        item {
            Spacer(modifier = Modifier.height(84.dp))
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

@Preview
@Composable
fun EmptyTasksListPreview() {
    ToDometerTheme {
        EmptyTasksListView()
    }
}
