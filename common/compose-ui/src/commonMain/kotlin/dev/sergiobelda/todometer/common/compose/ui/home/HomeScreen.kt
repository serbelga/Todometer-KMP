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

package dev.sergiobelda.todometer.common.compose.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.components.SwipeableTaskItem
import dev.sergiobelda.todometer.common.compose.ui.components.TaskListProgress
import dev.sergiobelda.todometer.common.compose.ui.components.ToDometerTitle
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.ToDometerContentLoadingProgress
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.ToDometerDivider
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.Alpha.applyMediumEmphasisAlpha
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.domain.model.TaskState
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.ToDometerIcons
import dev.sergiobelda.todometer.common.resources.ToDometerIllustrations
import dev.sergiobelda.todometer.common.resources.stringResource
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun HomeScreen(
    // TODO: Rename parameters
    navigateToAddTaskList: () -> Unit,
    navigateToEditTaskList: () -> Unit,
    navigateToAddTask: () -> Unit,
    navigateToTaskDetails: (String) -> Unit,
    onSelectTaskItem: (String) -> Unit,
    onDeleteTasksClick: () -> Unit,
    onClearSelectedTasks: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToAbout: () -> Unit,
    onTaskItemDoingClick: (String) -> Unit,
    onTaskItemDoneClick: (String) -> Unit,
    onTaskListItemClick: (String) -> Unit,
    onDeleteTaskListClick: () -> Unit,
    homeUiState: HomeUiState
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val snackbarHostState = remember { SnackbarHostState() }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val closeDrawer: suspend () -> Unit = {
        drawerState.close()
    }

    var swipedTaskId by remember { mutableStateOf("") }

    var deleteTaskAlertDialogState by remember { mutableStateOf(false) }
    var deleteTasksAlertDialogState by remember { mutableStateOf(false) }
    var deleteTaskListAlertDialogState by remember { mutableStateOf(false) }

    val defaultTaskListName = stringResource(MR.strings.default_task_list_name)

    var homeMoreDropdownExpanded by remember { mutableStateOf(false) }

    val cannotEditTaskList = stringResource(MR.strings.cannot_edit_this_task_list)
    val cannotDeleteTaskList = stringResource(MR.strings.cannot_delete_this_task_list)
    val snackbarActionLabel = stringResource(MR.strings.ok)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                HomeDrawerContent(
                    homeUiState.taskListSelected?.id ?: "",
                    defaultTaskListName,
                    homeUiState.taskLists,
                    onAddTaskList = {
                        scope.launch { closeDrawer() }
                        navigateToAddTaskList()
                    },
                    onTaskListItemClick = {
                        onTaskListItemClick(it)
                        scope.launch { closeDrawer() }
                    },
                    onSettingsItemClick = {
                        navigateToSettings()
                        scope.launch { closeDrawer() }
                    },
                    onAboutItemClick = {
                        navigateToAbout()
                        scope.launch { closeDrawer() }
                    }
                )
            }
        },
        gesturesEnabled = homeUiState.selectedTasks.isEmpty()
    ) {
        Scaffold(
            topBar = {
                HomeTopAppBar(
                    onClearSelectedTasksClick = onClearSelectedTasks,
                    selectedTasks = homeUiState.selectedTasks.size,
                    onDeleteSelectedTasksClick = { deleteTasksAlertDialogState = true },
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onMoreClick = { homeMoreDropdownExpanded = true },
                    onHomeMoreDropdownDismissRequest = { homeMoreDropdownExpanded = false },
                    homeMoreDropdownExpanded = homeMoreDropdownExpanded,
                    onEditTaskListClick = {
                        if (homeUiState.isDefaultTaskListSelected) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    cannotEditTaskList,
                                    snackbarActionLabel,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        } else {
                            navigateToEditTaskList()
                        }
                        homeMoreDropdownExpanded = false
                    },
                    onDeleteTaskListClick = {
                        if (homeUiState.isDefaultTaskListSelected) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    cannotDeleteTaskList,
                                    snackbarActionLabel,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        } else {
                            deleteTaskListAlertDialogState = true
                        }
                        homeMoreDropdownExpanded = false
                    },
                    taskListName = homeUiState.taskListSelected?.name ?: defaultTaskListName,
                    tasks = homeUiState.tasks
                )
            },
            content = { paddingValues ->
                if (deleteTaskAlertDialogState) {
                    DeleteTaskAlertDialog(
                        onDismissRequest = {
                            deleteTaskAlertDialogState = false
                            swipedTaskId = ""
                        },
                        onDeleteTaskClick = {
                            // TODO: Update
                            // onDeleteTasksClick()
                        }
                    )
                }
                if (deleteTasksAlertDialogState) {
                    DeleteTasksAlertDialog(
                        onDismissRequest = {
                            deleteTasksAlertDialogState = false
                            onClearSelectedTasks()
                        },
                        onDeleteTaskClick = { onDeleteTasksClick() }
                    )
                }
                if (deleteTaskListAlertDialogState) {
                    DeleteTaskListAlertDialog(
                        onDismissRequest = { deleteTaskListAlertDialogState = false },
                        onDeleteTaskListClick = {
                            onDeleteTaskListClick()
                            scope.launch { sheetState.hide() }
                        }
                    )
                }
                if (homeUiState.isLoadingTasks) {
                    ToDometerContentLoadingProgress()
                } else {
                    if (homeUiState.tasks.isEmpty()) {
                        HomeInfoIllustration(
                            ToDometerIllustrations.NoTasks,
                            stringResource(MR.strings.no_tasks)
                        )
                    } else {
                        TasksList(
                            homeUiState.tasks,
                            homeUiState.selectedTasks,
                            onDoingClick = onTaskItemDoingClick,
                            onDoneClick = onTaskItemDoneClick,
                            onTaskItemClick = { taskId ->
                                if (homeUiState.selectedTasks.contains(taskId)) {
                                    onSelectTaskItem(taskId)
                                } else {
                                    navigateToTaskDetails(taskId)
                                }
                            },
                            onTaskItemLongClick = onSelectTaskItem,
                            onSwipeToDismiss = {
                                deleteTaskAlertDialogState = true
                                swipedTaskId = it
                            },
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                }
            },
            floatingActionButton = {
                if (homeUiState.selectedTasks.isEmpty()) {
                    FloatingActionButton(
                        onClick = navigateToAddTask
                    ) {
                        Icon(
                            ToDometerIcons.Add,
                            contentDescription = stringResource(MR.strings.add_task)
                        )
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            snackbarHost = { SnackbarHost(snackbarHostState) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppBar(
    onMenuClick: () -> Unit,
    onMoreClick: () -> Unit,
    onHomeMoreDropdownDismissRequest: () -> Unit,
    homeMoreDropdownExpanded: Boolean,
    onEditTaskListClick: () -> Unit,
    onDeleteTaskListClick: () -> Unit,
    onClearSelectedTasksClick: () -> Unit,
    selectedTasks: Int,
    onDeleteSelectedTasksClick: () -> Unit,
    taskListName: String?,
    tasks: List<TaskItem>
) {
    val selectedMode = selectedTasks > 0
    // TODO: Animate tonalElevation
    val tonalElevation = if (selectedMode) 2.dp else 0.dp
    Surface(tonalElevation = tonalElevation) {
        Column {
            if (selectedMode) {
                // TODO: Animate appearance
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = onClearSelectedTasksClick) {
                            Icon(
                                ToDometerIcons.Close,
                                contentDescription = null
                            )
                        }
                    },
                    title = {
                        Text(text = "$selectedTasks selected")
                    },
                    actions = {
                        IconButton(onClick = onDeleteSelectedTasksClick) {
                            Icon(
                                ToDometerIcons.Delete,
                                contentDescription = null
                            )
                        }
                    }
                )
            } else {
                CenterAlignedTopAppBar(
                    title = {
                        ToDometerTitle()
                    },
                    navigationIcon = {
                        IconButton(onClick = onMenuClick) {
                            Icon(
                                ToDometerIcons.Menu,
                                contentDescription = stringResource(MR.strings.menu)
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = onMoreClick) {
                            Icon(
                                ToDometerIcons.MoreVert,
                                contentDescription = stringResource(MR.strings.more)
                            )
                        }
                        HomeMoreDropdownMenu(
                            onEditTaskListClick = onEditTaskListClick,
                            onDeleteTaskListClick = onDeleteTaskListClick,
                            expanded = homeMoreDropdownExpanded,
                            onDismissRequest = onHomeMoreDropdownDismissRequest
                        )
                    }
                )
            }
            TaskListProgress(taskListName, tasks)
            ToDometerDivider()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun TasksList(
    tasks: List<TaskItem>,
    selectedTasks: List<String>,
    onDoingClick: (String) -> Unit,
    onDoneClick: (String) -> Unit,
    onTaskItemClick: (String) -> Unit,
    onTaskItemLongClick: (String) -> Unit,
    onSwipeToDismiss: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val tasksDoing = tasks.filter { it.state == TaskState.DOING }
    val tasksDone = tasks.filter { it.state == TaskState.DONE }
    var areTasksDoneVisible by remember { mutableStateOf(false) }
    LazyColumn(modifier = modifier) {
        items(tasksDoing, key = { it.id }) { task ->
            // TODO: Improve this
            val selected = selectedTasks.contains(task.id)
            SwipeableTaskItem(
                taskItem = task,
                onDoingClick = onDoingClick,
                onDoneClick = onDoneClick,
                onTaskItemClick = onTaskItemClick,
                onTaskItemLongClick = onTaskItemLongClick,
                modifier = Modifier.animateItemPlacement(),
                selected = selected
            ) { onSwipeToDismiss(task.id) }
            ToDometerDivider(thickness = Dp.Hairline)
        }
        // TODO: Update this behavior
        if (tasksDone.isNotEmpty()) {
            item {
                ListItem(
                    headlineText = {
                        Text(
                            text = stringResource(
                                resource = MR.strings.completed_tasks,
                                tasksDone.size
                            )
                        )
                    },
                    trailingContent = {
                        if (areTasksDoneVisible) {
                            Icon(
                                ToDometerIcons.ExpandLess,
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                ToDometerIcons.ExpandMore,
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier
                        .animateItemPlacement()
                        .clickable { areTasksDoneVisible = !areTasksDoneVisible }
                )
            }
        }
        if (areTasksDoneVisible) {
            items(tasksDone, key = { it.id }) { task ->
                // TODO: Update this too
                SwipeableTaskItem(
                    task,
                    onDoingClick,
                    onDoneClick,
                    onTaskItemClick,
                    onTaskItemLongClick,
                    modifier = Modifier.animateItemPlacement()
                ) { onSwipeToDismiss(task.id) }
                ToDometerDivider()
            }
        }
        item {
            Spacer(modifier = Modifier.height(84.dp))
        }
    }
    if (tasksDoing.isEmpty() && !areTasksDoneVisible) {
        HomeInfoIllustration(
            ToDometerIllustrations.CompletedTasks,
            stringResource(MR.strings.you_have_completed_all_tasks),
            stringResource(MR.strings.congratulations)
        )
    }
}

@Composable
private fun HomeInfoIllustration(
    painter: Painter,
    text: String,
    secondaryText: String? = null
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center).padding(bottom = 72.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter,
                modifier = Modifier.size(220.dp).padding(bottom = 36.dp),
                contentDescription = null
            )
            Text(text = text)
            secondaryText?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.onSurface.applyMediumEmphasisAlpha(),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
