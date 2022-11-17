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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dev.sergiobelda.todometer.R
import dev.sergiobelda.todometer.common.compose.ui.components.task.TaskItem
import dev.sergiobelda.todometer.common.compose.ui.components.tasklist.TaskListItem
import dev.sergiobelda.todometer.common.compose.ui.components.title.ToDometerTitle
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.HorizontalDivider
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.Alpha.Disabled
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.Alpha.High
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.ToDometerTheme
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.sheetShape
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.domain.model.TaskList
import dev.sergiobelda.todometer.common.domain.model.TaskState
import dev.sergiobelda.todometer.common.domain.preference.AppTheme
import dev.sergiobelda.todometer.extensions.launchActivity
import dev.sergiobelda.todometer.glance.ToDometerWidgetReceiver
import dev.sergiobelda.todometer.preferences.appThemeMap
import dev.sergiobelda.todometer.ui.components.ToDometerAlertDialog
import dev.sergiobelda.todometer.ui.components.ToDometerContentLoadingProgress
import dev.sergiobelda.todometer.ui.components.ToDometerTopAppBar
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalLifecycleComposeApi::class
)
@Composable
internal fun HomeScreen(
    addTaskList: () -> Unit,
    editTaskList: () -> Unit,
    addTask: () -> Unit,
    openTask: (String) -> Unit,
    about: () -> Unit,
    homeViewModel: HomeViewModel = getViewModel()
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

    val homeUiState = homeViewModel.homeUiState
    val appTheme by homeViewModel.appTheme.collectAsStateWithLifecycle()

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
                        editTaskList()
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
                        about()
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
                        addTaskList = {
                            scope.launch {
                                closeDrawer()
                            }
                            addTaskList()
                        },
                        selectTaskList = {
                            homeViewModel.setTaskListSelected(it)
                            scope.launch { closeDrawer() }
                            updateToDometerWidgetData()
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
                            deleteTask = {
                                homeViewModel.deleteTask(selectedTask)
                                updateToDometerWidgetData()
                            }
                        )
                    }
                    if (deleteTaskListAlertDialogState) {
                        DeleteTaskListAlertDialog(
                            onDismissRequest = { deleteTaskListAlertDialogState = false },
                            deleteTaskList = {
                                homeViewModel.deleteTaskList()
                                updateToDometerWidgetData()
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
                                onDoingClick = {
                                    homeViewModel.setTaskDoing(it)
                                    updateToDometerWidgetData()
                                },
                                onDoneClick = {
                                    homeViewModel.setTaskDone(it)
                                    updateToDometerWidgetData()
                                },
                                onTaskItemClick = openTask,
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
                        onClick = addTask
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
private fun ChooseThemeAlertDialog(
    currentTheme: AppTheme,
    onDismissRequest: () -> Unit,
    chooseTheme: (theme: AppTheme) -> Unit
) {
    var themeSelected by remember { mutableStateOf(currentTheme) }
    ToDometerAlertDialog(
        title = {
            Text(
                text = stringResource(id = R.string.choose_theme),
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
                                .padding(horizontal = 16.dp)
                        ) {
                            RadioButton(
                                selected = themeSelected == appTheme,
                                onClick = { themeSelected = appTheme }
                            )
                            Text(
                                text = stringResource(appThemeOption.modeNameRes),
                                style = MaterialTheme.typography.bodyLarge,
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
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}

@Composable
private fun DeleteTaskListAlertDialog(onDismissRequest: () -> Unit, deleteTaskList: () -> Unit) {
    AlertDialog(
        icon = {
            Icon(
                Icons.Rounded.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = {
            Text(stringResource(id = R.string.delete_task_list))
        },
        onDismissRequest = onDismissRequest,
        text = {
            Text(stringResource(id = R.string.delete_task_list_question))
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
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}

@Composable
private fun DeleteTaskAlertDialog(onDismissRequest: () -> Unit, deleteTask: () -> Unit) {
    AlertDialog(
        title = {
            Text(stringResource(id = R.string.delete_task))
        },
        onDismissRequest = onDismissRequest,
        text = {
            Text(stringResource(id = R.string.delete_task_question))
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
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}

@Composable
private fun DrawerContent(
    selectedTaskListId: String,
    defaultTaskListName: String,
    taskLists: List<TaskList>,
    addTaskList: () -> Unit,
    selectTaskList: (String) -> Unit
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
        TextButton(onClick = addTaskList) {
            Text(stringResource(id = R.string.add_task_list))
        }
    }
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

@Composable
@Deprecated("To be removed")
private fun EmptyTaskListsView(addTaskList: () -> Unit) {
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
                stringResource(id = R.string.no_task_lists),
                modifier = Modifier.padding(bottom = 48.dp)
            )
            Button(onClick = addTaskList) {
                Text(text = stringResource(id = R.string.add_task_list))
            }
        }
    }
}

@Composable
private fun MoreBottomSheet(
    editTaskListEnabled: Boolean,
    editTaskListClick: () -> Unit,
    deleteTaskListEnabled: Boolean,
    deleteTaskListClick: () -> Unit,
    currentTheme: AppTheme,
    chooseThemeClick: () -> Unit,
    openSourceLicensesClick: () -> Unit,
    aboutClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 16.dp)
    ) {
        EditTaskListItem(editTaskListEnabled, editTaskListClick)
        DeleteTaskListItem(deleteTaskListEnabled, deleteTaskListClick)
        HorizontalDivider()
        ChooseThemeListItem(currentTheme, chooseThemeClick)
        HorizontalDivider()
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            TextButton(onClick = openSourceLicensesClick) {
                Text(
                    stringResource(id = R.string.open_source_licenses),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Text("·")
            TextButton(onClick = aboutClick) {
                Text(
                    stringResource(id = R.string.about),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditTaskListItem(
    editTaskListEnabled: Boolean,
    editTaskListClick: () -> Unit
) {
    ListItem(
        headlineText = {
            Text(
                stringResource(id = R.string.edit_task_list),
                style = MaterialTheme.typography.titleSmall
            )
        },
        supportingText = {
            if (!editTaskListEnabled) {
                Text(
                    stringResource(id = R.string.cannot_edit_this_task_list),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        leadingContent = {
            Icon(
                Icons.Outlined.Edit,
                contentDescription = stringResource(id = R.string.edit_task_list)
            )
        },
        modifier = Modifier.clickable(
            enabled = editTaskListEnabled,
            onClick = editTaskListClick
        ).height(MoreBottomSheetListItemHeight).alpha(if (editTaskListEnabled) High else Disabled)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeleteTaskListItem(
    deleteTaskListEnabled: Boolean,
    deleteTaskListClick: () -> Unit
) {
    ListItem(
        headlineText = {
            Text(
                stringResource(id = R.string.delete_task_list),
                style = MaterialTheme.typography.titleSmall
            )
        },
        supportingText = {
            if (!deleteTaskListEnabled) {
                Text(
                    stringResource(id = R.string.cannot_delete_this_task_list),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        leadingContent = {
            Icon(
                Icons.Outlined.Delete,
                contentDescription = stringResource(id = R.string.delete_task_list)
            )
        },
        modifier = Modifier.clickable(
            enabled = deleteTaskListEnabled,
            onClick = deleteTaskListClick
        ).height(MoreBottomSheetListItemHeight).alpha(if (deleteTaskListEnabled) High else Disabled)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChooseThemeListItem(
    currentTheme: AppTheme,
    chooseThemeClick: () -> Unit
) {
    ListItem(
        headlineText = {
            Text(
                stringResource(id = R.string.theme),
                style = MaterialTheme.typography.titleSmall
            )
        },
        supportingText = {
            appThemeMap[currentTheme]?.modeNameRes?.let {
                Text(
                    stringResource(it),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        },
        leadingContent = {
            appThemeMap[currentTheme]?.themeIconRes?.let {
                Icon(
                    painterResource(it),
                    contentDescription = stringResource(id = R.string.theme)
                )
            }
        },
        modifier = Modifier.height(MoreBottomSheetListItemHeight)
            .clickable(onClick = chooseThemeClick)
    )
}

private fun updateToDometerWidgetData() {
    ToDometerWidgetReceiver().updateData()
}

private val MoreBottomSheetListItemHeight = 64.dp
