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

package dev.sergiobelda.todometer.ui.home

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dev.sergiobelda.todometer.R
import dev.sergiobelda.todometer.common.android.extensions.launchActivity
import dev.sergiobelda.todometer.common.compose.ui.components.ToDometerContentLoadingProgress
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
import dev.sergiobelda.todometer.ui.components.ToDometerTopAppBar
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
internal fun HomeScreen(
    navigateToAddTaskList: () -> Unit,
    navigateToEditTaskList: () -> Unit,
    navigateToAddTask: () -> Unit,
    onTaskItemClick: (String) -> Unit,
    onDeleteTaskClick: (String) -> Unit,
    navigateToAboutClick: () -> Unit,
    onTaskItemDoingClick: (String) -> Unit,
    onTaskItemDoneClick: (String) -> Unit,
    onTaskListItemClick: (String) -> Unit,
    onDeleteTaskListClick: () -> Unit,
    onChooseThemeClick: (AppTheme) -> Unit,
    homeUiState: HomeUiState,
    appTheme: AppTheme = AppTheme.FOLLOW_SYSTEM
) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    // TODO: Use skipHalfExpanded when available.
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val closeDrawer: suspend () -> Unit = {
        drawerState.close()
    }

    var selectedTask by remember { mutableStateOf("") }
    var deleteTaskAlertDialogState by remember { mutableStateOf(false) }
    var deleteTaskListAlertDialogState by remember { mutableStateOf(false) }
    var chooseThemeAlertDialogState by remember { mutableStateOf(false) }

    val defaultTaskListName = stringResource(id = R.string.default_task_list_name)

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
                        context.launchActivity<OssLicensesMenuActivity>()
                    }
                },
                aboutClick = {
                    scope.launch {
                        sheetState.hide()
                        navigateToAboutClick()
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
                                R.drawable.no_tasks,
                                stringResource(id = R.string.no_tasks)
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
                            contentDescription = stringResource(id = R.string.add_task)
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
            text = stringResource(id = R.string.task_lists),
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.weight(1f))
        TextButton(onClick = onAddTaskList) {
            Text(stringResource(id = R.string.add_task_list))
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
                            text = stringResource(id = R.string.completed_tasks, tasksDone.size)
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
            R.drawable.completed_tasks,
            stringResource(id = R.string.you_have_completed_all_tasks),
            stringResource(id = R.string.congratulations)
        )
    }
}

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalAnimationGraphicsApi::class
)
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
                    contentDescription = stringResource(id = R.string.delete_task),
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
    @DrawableRes drawableRes: Int,
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
                painterResource(drawableRes),
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
