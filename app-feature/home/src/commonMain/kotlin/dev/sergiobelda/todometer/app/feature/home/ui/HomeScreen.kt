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

package dev.sergiobelda.todometer.app.feature.home.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.sergiobelda.navigation.compose.extended.annotation.NavDestination
import dev.sergiobelda.todometer.app.common.designsystem.components.TodometerDivider
import dev.sergiobelda.todometer.app.common.designsystem.theme.Alpha.applyMediumEmphasisAlpha
import dev.sergiobelda.todometer.app.common.ui.actions.SystemBackHandler
import dev.sergiobelda.todometer.app.common.ui.components.TaskItem
import dev.sergiobelda.todometer.app.common.ui.components.TaskListProgress
import dev.sergiobelda.todometer.app.common.ui.components.TodometerTitle
import dev.sergiobelda.todometer.app.common.ui.loading.ContentLoadingProgress
import dev.sergiobelda.todometer.app.common.ui.values.SectionPadding
import dev.sergiobelda.todometer.common.designsystem.resources.images.Images
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Add
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Close
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Delete
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.ExpandLess
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.ExpandMore
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Menu
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.MoreVert
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.PushPin
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.PushPinFilled
import dev.sergiobelda.todometer.common.designsystem.resources.images.illustrations.CompletedTasks
import dev.sergiobelda.todometer.common.designsystem.resources.images.illustrations.NoTasks
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.resources.TodometerResources
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

// TODO: Resolve LongMethod issue.
@Suppress("LongMethod")
@NavDestination(
    destinationId = "home",
    name = "Home"
)
@Composable
fun HomeScreen(
    navigateToAddTaskList: () -> Unit,
    navigateToEditTaskList: () -> Unit,
    navigateToAddTask: () -> Unit,
    navigateToTaskDetails: (String) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToAbout: () -> Unit,
    viewModel: HomeViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    fun drawerAction(action: () -> Unit) {
        coroutineScope.launch {
            drawerState.close()
            action.invoke()
        }
    }

    var swipedTaskId by remember { mutableStateOf("") }

    var deleteTaskAlertDialogState by remember { mutableStateOf(false) }
    var deleteTasksAlertDialogState by remember { mutableStateOf(false) }
    var deleteTaskListAlertDialogState by remember { mutableStateOf(false) }

    val defaultTaskListName = TodometerResources.strings.defaultTaskListName

    var homeMoreDropdownExpanded by remember { mutableStateOf(false) }
    val closeHomeMoreDropdown = { homeMoreDropdownExpanded = false }

    val cannotEditTaskList = TodometerResources.strings.cannotEditThisTaskList
    val cannotDeleteTaskList = TodometerResources.strings.cannotDeleteThisTaskList
    val snackbarActionLabel = TodometerResources.strings.ok

    SystemBackHandler(enabled = viewModel.state.selectionMode) { viewModel.clearSelectedTasks() }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            HomeDrawerContent(
                viewModel.state.taskListSelected?.id ?: "",
                defaultTaskListName,
                viewModel.state.taskLists,
                onAddTaskList = {
                    drawerAction(navigateToAddTaskList)
                },
                onTaskListItemClick = {
                    drawerAction(action = { viewModel.setTaskListSelected(it) })
                },
                onSettingsItemClick = {
                    drawerAction(navigateToSettings)
                },
                onAboutItemClick = {
                    drawerAction(navigateToAbout)
                }
            )
        },
        gesturesEnabled = !viewModel.state.selectionMode
    ) {
        Scaffold(
            topBar = {
                HomeTopAppBar(
                    onMenuClick = { coroutineScope.launch { drawerState.open() } },
                    onMoreClick = { homeMoreDropdownExpanded = true },
                    onHomeMoreDropdownDismissRequest = closeHomeMoreDropdown,
                    homeMoreDropdownExpanded = homeMoreDropdownExpanded,
                    onEditTaskListClick = {
                        if (viewModel.state.isDefaultTaskListSelected) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    cannotEditTaskList,
                                    snackbarActionLabel,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        } else {
                            navigateToEditTaskList()
                        }
                        closeHomeMoreDropdown()
                    },
                    onDeleteTaskListClick = {
                        if (viewModel.state.isDefaultTaskListSelected) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    cannotDeleteTaskList,
                                    snackbarActionLabel,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        } else {
                            deleteTaskListAlertDialogState = true
                        }
                        closeHomeMoreDropdown()
                    },
                    taskListName = viewModel.state.taskListSelected?.name ?: defaultTaskListName,
                    tasks = viewModel.state.tasks,
                    selectionMode = viewModel.state.selectionMode,
                    selectedTasks = viewModel.state.selectedTasks,
                    onClearSelectedTasksClick = viewModel::clearSelectedTasks,
                    onToggleSelectedTasksPinnedValueClick = viewModel::toggleSelectedTasksPinnedValue,
                    onDeleteSelectedTasksClick = { deleteTasksAlertDialogState = true }
                )
            },
            content = { paddingValues ->
                if (deleteTaskAlertDialogState) {
                    DeleteTaskAlertDialog(
                        onDismissRequest = {
                            deleteTaskAlertDialogState = false
                            swipedTaskId = ""
                        },
                        onDeleteTaskClick = { viewModel.deleteTask(swipedTaskId) }
                    )
                }
                if (deleteTasksAlertDialogState) {
                    DeleteTasksAlertDialog(
                        onDismissRequest = {
                            deleteTasksAlertDialogState = false
                        },
                        onDeleteTaskClick = viewModel::deleteSelectedTasks
                    )
                }
                if (deleteTaskListAlertDialogState) {
                    DeleteTaskListAlertDialog(
                        onDismissRequest = { deleteTaskListAlertDialogState = false },
                        onDeleteTaskListClick = viewModel::deleteTaskList
                    )
                }
                if (viewModel.state.isLoadingTasks) {
                    ContentLoadingProgress()
                } else {
                    TasksListView(
                        tasksDoingPinned = viewModel.state.tasksDoingPinned,
                        tasksDoingNotPinned = viewModel.state.tasksDoingNotPinned,
                        tasksDone = viewModel.state.tasksDone,
                        selectedTasksIds = viewModel.state.selectedTasksIds,
                        onDoingClick = { viewModel.setTaskDoing(it) },
                        onDoneClick = { viewModel.setTaskDone(it) },
                        onTaskItemClick = { taskId ->
                            if (viewModel.state.selectionMode) {
                                viewModel.toggleSelectTask(taskId)
                            } else {
                                navigateToTaskDetails(taskId)
                            }
                        },
                        onTaskItemLongClick = { viewModel.toggleSelectTask(it) },
                        onSwipeToDismiss = {
                            deleteTaskAlertDialogState = true
                            swipedTaskId = it
                        },
                        selectionMode = viewModel.state.selectionMode,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            },
            floatingActionButton = {
                HomeFloatingActionButton(
                    visible = !viewModel.state.selectionMode,
                    navigateToAddTask = navigateToAddTask
                )
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
    selectionMode: Boolean,
    selectedTasks: ImmutableList<TaskItem>,
    onClearSelectedTasksClick: () -> Unit,
    onToggleSelectedTasksPinnedValueClick: () -> Unit,
    onDeleteSelectedTasksClick: () -> Unit,
    taskListName: String?,
    tasks: ImmutableList<TaskItem>
) {
    val tonalElevation by animateDpAsState(
        if (selectionMode) HomeTopAppBarTonalElevation else 0.dp,
        animationSpec = tween(
            durationMillis = HomeTopAppBarAnimationDuration,
            easing = FastOutSlowInEasing
        )
    )
    Surface(tonalElevation = tonalElevation) {
        Column {
            Box {
                if (selectionMode) {
                    SelectedTasksTopAppBar(
                        onClearSelectedTasksClick = onClearSelectedTasksClick,
                        onToggleSelectedTasksPinnedValueClick = onToggleSelectedTasksPinnedValueClick,
                        onDeleteSelectedTasksClick = onDeleteSelectedTasksClick,
                        selectedTasks = selectedTasks
                    )
                } else {
                    CenterAlignedTopAppBar(
                        title = {
                            TodometerTitle()
                        },
                        navigationIcon = {
                            IconButton(onClick = onMenuClick) {
                                Icon(
                                    Images.Icons.Menu,
                                    contentDescription = TodometerResources.strings.menu
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = onMoreClick) {
                                Icon(
                                    Images.Icons.MoreVert,
                                    contentDescription = TodometerResources.strings.more
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
            }
            TaskListProgress(taskListName, tasks)
            TodometerDivider()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectedTasksTopAppBar(
    onClearSelectedTasksClick: () -> Unit,
    onToggleSelectedTasksPinnedValueClick: () -> Unit,
    onDeleteSelectedTasksClick: () -> Unit,
    selectedTasks: ImmutableList<TaskItem>
) {
    val atLeastOneNotPinnedTaskItem = remember(selectedTasks) { selectedTasks.any { !it.isPinned } }
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onClearSelectedTasksClick) {
                Icon(
                    Images.Icons.Close,
                    contentDescription = null
                )
            }
        },
        title = {
            Text(text = TodometerResources.strings.selectedTasks(selectedTasks.size))
        },
        actions = {
            IconButton(onClick = onToggleSelectedTasksPinnedValueClick) {
                Icon(
                    imageVector = if (atLeastOneNotPinnedTaskItem) {
                        Images.Icons.PushPin
                    } else {
                        Images.Icons.PushPinFilled
                    },
                    contentDescription = if (atLeastOneNotPinnedTaskItem) {
                        TodometerResources.strings.notPinnedTask
                    } else {
                        TodometerResources.strings.pinnedTask
                    }
                )
            }
            IconButton(onClick = onDeleteSelectedTasksClick) {
                Icon(
                    Images.Icons.Delete,
                    contentDescription = TodometerResources.strings.deleteTasks
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors()
    )
}

@Composable
private fun HomeFloatingActionButton(
    visible: Boolean,
    navigateToAddTask: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn() + fadeIn(),
        exit = scaleOut() + fadeOut()
    ) {
        FloatingActionButton(onClick = navigateToAddTask) {
            Icon(
                Images.Icons.Add,
                contentDescription = TodometerResources.strings.addTask
            )
        }
    }
}

// TODO: Resolve LongMethod issue.
@Suppress("LongMethod")
@Composable
private fun TasksListView(
    tasksDoingPinned: ImmutableList<TaskItem>,
    tasksDoingNotPinned: ImmutableList<TaskItem>,
    tasksDone: ImmutableList<TaskItem>,
    selectedTasksIds: ImmutableList<String>,
    onDoingClick: (String) -> Unit,
    onDoneClick: (String) -> Unit,
    onTaskItemClick: (String) -> Unit,
    onTaskItemLongClick: (String) -> Unit,
    onSwipeToDismiss: (String) -> Unit,
    selectionMode: Boolean,
    modifier: Modifier = Modifier
) {
    var areTasksDoneVisible by remember { mutableStateOf(false) }
    if ((tasksDoingPinned + tasksDoingNotPinned + tasksDone).isEmpty()) {
        HomeInfoIllustration(
            Images.Illustrations.NoTasks,
            TodometerResources.strings.noTasks
        )
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            if (tasksDoingPinned.isNotEmpty()) {
                item {
                    TasksSeparator(TodometerResources.strings.pinned)
                }
            }
            taskItems(
                tasks = tasksDoingPinned,
                onDoingClick = onDoingClick,
                onDoneClick = onDoneClick,
                onTaskItemClick = onTaskItemClick,
                onTaskItemLongClick = onTaskItemLongClick,
                onSwipeToDismiss = onSwipeToDismiss,
                selectionMode = selectionMode,
                selectedTasksIds = selectedTasksIds
            )
            if (tasksDoingPinned.isNotEmpty() && tasksDoingNotPinned.isNotEmpty()) {
                item {
                    TasksSeparator(TodometerResources.strings.others)
                }
            }
            taskItems(
                tasks = tasksDoingNotPinned,
                onDoingClick = onDoingClick,
                onDoneClick = onDoneClick,
                onTaskItemClick = onTaskItemClick,
                onTaskItemLongClick = onTaskItemLongClick,
                onSwipeToDismiss = onSwipeToDismiss,
                selectionMode = selectionMode,
                selectedTasksIds = selectedTasksIds
            )
            if (tasksDone.isNotEmpty()) {
                item {
                    CompletedTasksHeader(
                        completedTasks = tasksDone.size,
                        enabled = !selectionMode,
                        expanded = areTasksDoneVisible,
                        showExpandIcon = !selectionMode,
                        onClick = { areTasksDoneVisible = !areTasksDoneVisible }
                    )
                }
            }
            if (areTasksDoneVisible || selectionMode) {
                taskItems(
                    tasks = tasksDone,
                    onDoingClick = onDoingClick,
                    onDoneClick = onDoneClick,
                    onTaskItemClick = onTaskItemClick,
                    onTaskItemLongClick = onTaskItemLongClick,
                    onSwipeToDismiss = onSwipeToDismiss,
                    selectionMode = selectionMode,
                    selectedTasksIds = selectedTasksIds
                )
            }
            item {
                Spacer(modifier = Modifier.height(HomeTaskListAreaBottomPadding))
            }
        }
        if ((tasksDoingPinned + tasksDoingNotPinned).isEmpty() && !areTasksDoneVisible) {
            HomeInfoIllustration(
                Images.Illustrations.CompletedTasks,
                TodometerResources.strings.youHaveCompletedAllTasks,
                TodometerResources.strings.congratulations
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.taskItems(
    tasks: ImmutableList<TaskItem>,
    onDoingClick: (String) -> Unit,
    onDoneClick: (String) -> Unit,
    onTaskItemClick: (String) -> Unit,
    onTaskItemLongClick: (String) -> Unit,
    onSwipeToDismiss: (String) -> Unit,
    selectionMode: Boolean,
    selectedTasksIds: ImmutableList<String>
) {
    items(
        tasks,
        key = { it.id },
        contentType = { it }
    ) { taskItem ->
        TaskItem(
            taskItem = taskItem,
            onDoingClick = { onDoingClick(taskItem.id) },
            onDoneClick = { onDoneClick(taskItem.id) },
            onTaskItemClick = { onTaskItemClick(taskItem.id) },
            onTaskItemLongClick = { onTaskItemLongClick(taskItem.id) },
            onSwipeToDismiss = { onSwipeToDismiss(taskItem.id) },
            modifier = Modifier.animateItem(),
            swipeable = !selectionMode,
            checkEnabled = selectionMode,
            selected = selectedTasksIds.contains(taskItem.id)
        )
    }
}

@Composable
private fun TasksSeparator(
    text: String
) {
    Text(
        text,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier
            .padding(horizontal = SectionPadding, vertical = 8.dp)
    )
}

@Composable
private fun CompletedTasksHeader(
    completedTasks: Int,
    enabled: Boolean,
    expanded: Boolean,
    showExpandIcon: Boolean,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = {
            Text(
                text = TodometerResources.strings.completedTasks(completedTasks)
            )
        },
        trailingContent = {
            if (showExpandIcon) {
                if (expanded) {
                    Icon(
                        Images.Icons.ExpandLess,
                        contentDescription = null
                    )
                } else {
                    Icon(
                        Images.Icons.ExpandMore,
                        contentDescription = null
                    )
                }
            }
        },
        modifier = Modifier.clickable(
            enabled = enabled,
            onClick = onClick
        )
    )
}

@Composable
private fun HomeInfoIllustration(
    imageVector: ImageVector,
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
                imageVector,
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

private val HomeTopAppBarTonalElevation: Dp = 4.dp
private const val HomeTopAppBarAnimationDuration: Int = 400
private val HomeTaskListAreaBottomPadding: Dp = 84.dp
