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

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.components.ToDometerContentLoadingProgress
import dev.sergiobelda.todometer.common.compose.ui.components.ToDometerTopAppBar
import dev.sergiobelda.todometer.common.compose.ui.components.task.TaskItem
import dev.sergiobelda.todometer.common.compose.ui.components.tasklist.TaskListItem
import dev.sergiobelda.todometer.common.compose.ui.components.title.ToDometerTitle
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.HorizontalDivider
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.ToDometerTheme
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.sheetShape
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.domain.model.TaskList
import dev.sergiobelda.todometer.common.domain.model.TaskState
import dev.sergiobelda.todometer.common.domain.preference.AppTheme
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.ToDometerIcons
import dev.sergiobelda.todometer.common.resources.ToDometerIllustrations
import dev.sergiobelda.todometer.common.resources.painterResource
import dev.sergiobelda.todometer.common.resources.stringResource
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun HomeScreen(
    navigateToAddTaskList: () -> Unit,
    navigateToEditTaskList: () -> Unit,
    navigateToAddTask: () -> Unit,
    onTaskItemClick: (String) -> Unit,
    onDeleteTaskClick: (String) -> Unit,
    navigateToOpenSourceLicenses: () -> Unit,
    navigateToAbout: () -> Unit,
    onTaskItemDoingClick: (String) -> Unit,
    onTaskItemDoneClick: (String) -> Unit,
    onTaskListItemClick: (String) -> Unit,
    onDeleteTaskListClick: () -> Unit,
    onChooseThemeClick: (AppTheme) -> Unit,
    homeUiState: HomeUiState,
    appTheme: AppTheme = AppTheme.FOLLOW_SYSTEM
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val closeDrawer: suspend () -> Unit = {
        drawerState.close()
    }

    var selectedTask by remember { mutableStateOf("") }
    var deleteTaskAlertDialogState by remember { mutableStateOf(false) }
    var deleteTaskListAlertDialogState by remember { mutableStateOf(false) }
    var chooseThemeAlertDialogState by remember { mutableStateOf(false) }

    val defaultTaskListName = stringResource(resource = MR.strings.default_task_list_name)

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetElevation = 16.dp,
        sheetShape = sheetShape,
        sheetContent = {
            MoreBottomSheet(
                editTaskListClick = {
                    scope.launch {
                        sheetState.hide()
                        navigateToEditTaskList()
                    }
                },
                editTaskListEnabled = !homeUiState.isDefaultTaskListSelected,
                deleteTaskListClick = {
                    deleteTaskListAlertDialogState = true
                },
                deleteTaskListEnabled = !homeUiState.isDefaultTaskListSelected,
                currentTheme = appTheme,
                chooseThemeClick = {
                    chooseThemeAlertDialogState = true
                },
                openSourceLicensesClick = {
                    scope.launch {
                        sheetState.hide()
                        navigateToOpenSourceLicenses()
                    }
                },
                aboutClick = {
                    scope.launch {
                        sheetState.hide()
                        navigateToAbout()
                    }
                }
            )
        },
        // TODO: Remove this line when ModalBottomSheetLayout is available in material3.
        sheetBackgroundColor = MaterialTheme.colorScheme.surface
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    DrawerContent(
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
                        }
                    )
                }
            }
        ) {
            Scaffold(
                topBar = {
                    ToDometerTopAppBar(
                        onMenuClick = {
                            scope.launch { drawerState.open() }
                        },
                        onMoreClick = {
                            scope.launch { sheetState.show() }
                        },
                        homeUiState.taskListSelected?.name ?: defaultTaskListName,
                        homeUiState.tasks
                    )
                },
                content = { paddingValues ->
                    if (deleteTaskAlertDialogState) {
                        DeleteTaskAlertDialog(
                            onDismissRequest = { deleteTaskAlertDialogState = false },
                            onDeleteTaskClick = { onDeleteTaskClick(selectedTask) }
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
                    if (chooseThemeAlertDialogState) {
                        ChooseThemeAlertDialog(
                            currentTheme = appTheme,
                            onDismissRequest = { chooseThemeAlertDialogState = false },
                            onChooseThemeClick = onChooseThemeClick
                        )
                    }
                    if (homeUiState.isLoadingTasks) {
                        ToDometerContentLoadingProgress()
                    } else {
                        if (homeUiState.tasks.isEmpty()) {
                            TaskListIllustration(
                                painterResource(resource = ToDometerIllustrations.noTasks),
                                stringResource(resource = MR.strings.no_tasks)
                            )
                        } else {
                            TasksListView(
                                homeUiState.tasks,
                                onDoingClick = onTaskItemDoingClick,
                                onDoneClick = onTaskItemDoneClick,
                                onTaskItemClick = onTaskItemClick,
                                onTaskItemLongClick = {
                                    deleteTaskAlertDialogState = true
                                    selectedTask = it
                                },
                                onSwipeToDismiss = {
                                    deleteTaskAlertDialogState = true
                                    selectedTask = it
                                },
                                modifier = Modifier.padding(paddingValues)
                            )
                        }
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = navigateToAddTask
                    ) {
                        Icon(
                            Icons.Rounded.Add,
                            contentDescription = stringResource(resource = MR.strings.add_task)
                        )
                    }
                },
                floatingActionButtonPosition = FabPosition.End
            )
        }
    }
}

@Composable
private fun DrawerContent(
    selectedTaskListId: String,
    defaultTaskListName: String,
    taskLists: List<TaskList>,
    onAddTaskList: () -> Unit,
    onTaskListItemClick: (String) -> Unit
) {
    Box(
        modifier = Modifier.height(56.dp).fillMaxWidth()
    ) {
        ToDometerTitle(
            modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp)
        )
    }
    HorizontalDivider(modifier = Modifier.padding(start = 16.dp, end = 16.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(56.dp)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(resource = MR.strings.task_lists),
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.weight(1f))
        TextButton(onClick = onAddTaskList) {
            Text(stringResource(resource = MR.strings.add_task_list))
        }
    }
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        item {
            TaskListItem(defaultTaskListName, selectedTaskListId == "") {
                onTaskListItemClick("")
            }
        }
        items(taskLists) { taskList ->
            TaskListItem(taskList.name, taskList.id == selectedTaskListId) {
                onTaskListItemClick(taskList.id)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun TasksListView(
    tasks: List<TaskItem>,
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
            SwipeableTaskItem(
                task,
                onDoingClick,
                onDoneClick,
                onTaskItemClick,
                onTaskItemLongClick,
                modifier = Modifier.animateItemPlacement()
            ) { onSwipeToDismiss(task.id) }
        }
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
                                Icons.Rounded.ExpandLess,
                                contentDescription = null
                            )
                        } else {
                            Icon(Icons.Rounded.ExpandMore, contentDescription = null)
                        }
                    },
                    modifier = Modifier.animateItemPlacement()
                        .clickable { areTasksDoneVisible = !areTasksDoneVisible }
                )
            }
        }
        if (areTasksDoneVisible) {
            items(tasksDone, key = { it.id }) { task ->
                SwipeableTaskItem(
                    task,
                    onDoingClick,
                    onDoneClick,
                    onTaskItemClick,
                    onTaskItemLongClick,
                    modifier = Modifier.animateItemPlacement()
                ) { onSwipeToDismiss(task.id) }
            }
        }
        item {
            Spacer(modifier = Modifier.height(84.dp))
        }
    }
    if (tasksDoing.isEmpty() && !areTasksDoneVisible) {
        TaskListIllustration(
            painterResource(resource = ToDometerIllustrations.completedTasks),
            stringResource(resource = MR.strings.you_have_completed_all_tasks),
            stringResource(resource = MR.strings.congratulations)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SwipeableTaskItem(
    taskItem: TaskItem,
    onDoingClick: (String) -> Unit,
    onDoneClick: (String) -> Unit,
    onTaskItemClick: (String) -> Unit,
    onTaskItemLongClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSwipeToDismiss: () -> Unit
) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToEnd) {
                onSwipeToDismiss()
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
                if (dismissState.targetValue == DismissValue.Default) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
                animationSpec = tween(
                    durationMillis = 400,
                    easing = FastOutSlowInEasing
                )
            )
            val tint by animateColorAsState(
                if (dismissState.targetValue == DismissValue.Default) ToDometerTheme.toDometerColors.onSurfaceMediumEmphasis else MaterialTheme.colorScheme.onError,
                animationSpec = tween(
                    durationMillis = 400,
                    easing = FastOutSlowInEasing
                )
            )
            // TODO: Enable AVD when available in multiplatform.
            // val icon = AnimatedImageVector.animatedVectorResource(R.drawable.avd_delete)
            Box(
                Modifier.fillMaxSize().background(color).padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                // TODO: Enable AVD when available in multiplatform.
                /*
                Icon(
                    rememberAnimatedVectorPainter(
                        icon,
                        atEnd = dismissState.targetValue == DismissValue.DismissedToEnd
                    ),
                    contentDescription = stringResource(resource = MR.strings.delete_task),
                    tint = tint
                )
                */
                Icon(
                    painter = painterResource(resource = ToDometerIcons.delete),
                    contentDescription = stringResource(resource = MR.strings.delete_task),
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
                taskItem,
                onDoingClick = onDoingClick,
                onDoneClick = onDoneClick,
                onClick = onTaskItemClick,
                onLongClick = onTaskItemLongClick,
                modifier = Modifier.clip(RoundedCornerShape(dp))
            )
        },
        modifier = modifier
    )
}

@Composable
private fun TaskListIllustration(
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
                    color = ToDometerTheme.toDometerColors.onSurfaceMediumEmphasis,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
