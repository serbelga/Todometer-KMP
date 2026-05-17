/*
 * Copyright 2025 Sergio Belda
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.sergiobelda.fonament.presentation.ui.FonamentContent
import dev.sergiobelda.todometer.app.common.designsystem.components.TodometerDivider
import dev.sergiobelda.todometer.app.common.designsystem.theme.Alpha.applyMediumEmphasisAlpha
import dev.sergiobelda.todometer.app.common.ui.actions.SystemBackHandler
import dev.sergiobelda.todometer.app.common.ui.components.TaskItem
import dev.sergiobelda.todometer.app.common.ui.components.TaskListProgress
import dev.sergiobelda.todometer.app.common.ui.components.TodometerTitle
import dev.sergiobelda.todometer.app.common.ui.loading.ContentLoadingProgress
import dev.sergiobelda.todometer.app.common.ui.values.SectionPadding
import dev.sergiobelda.todometer.app.feature.home.navigation.HomeNavigationEvent
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
import dev.sergiobelda.todometer.common.resources.Res
import dev.sergiobelda.todometer.common.resources.add_task
import dev.sergiobelda.todometer.common.resources.cannot_delete_this_task_list
import dev.sergiobelda.todometer.common.resources.cannot_edit_this_task_list
import dev.sergiobelda.todometer.common.resources.completed_tasks
import dev.sergiobelda.todometer.common.resources.congratulations
import dev.sergiobelda.todometer.common.resources.default_task_list_name
import dev.sergiobelda.todometer.common.resources.delete_tasks
import dev.sergiobelda.todometer.common.resources.menu
import dev.sergiobelda.todometer.common.resources.more
import dev.sergiobelda.todometer.common.resources.no_tasks
import dev.sergiobelda.todometer.common.resources.not_pinned_task
import dev.sergiobelda.todometer.common.resources.ok
import dev.sergiobelda.todometer.common.resources.others
import dev.sergiobelda.todometer.common.resources.pinned
import dev.sergiobelda.todometer.common.resources.pinned_task
import dev.sergiobelda.todometer.common.resources.selected_tasks
import dev.sergiobelda.todometer.common.resources.you_have_completed_all_tasks
import dev.sergiobelda.todometer.common.ui.id.elementId
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

internal data object HomeContent : FonamentContent<HomeUIState, HomeContentState>() {
    @Composable
    override fun createContentState(uiState: HomeUIState): HomeContentState = rememberHomeContentState()

    @Composable
    override fun Content(
        uiState: HomeUIState,
        contentState: HomeContentState,
        modifier: Modifier,
    ) {
        val defaultTaskListName = stringResource(Res.string.default_task_list_name)
        val cannotEditTaskList = stringResource(Res.string.cannot_edit_this_task_list)
        val cannotDeleteTaskList = stringResource(Res.string.cannot_delete_this_task_list)
        val snackbarActionLabel = stringResource(Res.string.ok)

        SystemBackHandler(enabled = uiState.selectionMode) { onEvent(HomeEvent.ClearSelectedTasks) }

        ModalNavigationDrawer(
            drawerState = contentState.drawerState,
            drawerContent = {
                HomeDrawerContent(
                    uiState.taskListSelected?.id ?: "",
                    defaultTaskListName,
                    uiState.taskLists,
                    onAddTaskList = {
                        onEvent(
                            HomeEvent.CloseDrawer(
                                onClose = { onEvent(HomeNavigationEvent.NavigateToAddTaskList) },
                            ),
                        )
                    },
                    onTaskListItemClick = {
                        onEvent(HomeEvent.CloseDrawer())
                        onEvent(HomeEvent.SetTaskListSelected(it))
                    },
                    onSettingsItemClick = {
                        onEvent(
                            HomeEvent.CloseDrawer(
                                onClose = { onEvent(HomeNavigationEvent.NavigateToSettings) },
                            ),
                        )
                    },
                    onAboutItemClick = {
                        onEvent(
                            HomeEvent.CloseDrawer(
                                onClose = { onEvent(HomeNavigationEvent.NavigateToAbout) },
                            ),
                        )
                    },
                )
            },
            gesturesEnabled = !uiState.selectionMode,
        ) {
            Scaffold(
                topBar = {
                    HomeTopAppBar(
                        onMenuClick = { onEvent(HomeEvent.OpenDrawer) },
                        onMoreClick = { onEvent(HomeEvent.ShowHomeMoreDropdown) },
                        onHomeMoreDropdownDismissRequest = { onEvent(HomeEvent.DismissHomeMoreDropdown) },
                        homeMoreDropdownExpanded = contentState.homeMoreDropdownExpanded,
                        onEditTaskListClick = {
                            if (uiState.isDefaultTaskListSelected) {
                                contentState.coroutineScope.launch {
                                    contentState.snackbarHostState.showSnackbar(
                                        cannotEditTaskList,
                                        snackbarActionLabel,
                                        duration = SnackbarDuration.Short,
                                    )
                                }
                            } else {
                                onEvent(HomeNavigationEvent.NavigateToEditTaskList)
                            }
                            onEvent(HomeEvent.DismissHomeMoreDropdown)
                        },
                        onDeleteTaskListClick = {
                            if (uiState.isDefaultTaskListSelected) {
                                contentState.coroutineScope.launch {
                                    contentState.snackbarHostState.showSnackbar(
                                        cannotDeleteTaskList,
                                        snackbarActionLabel,
                                        duration = SnackbarDuration.Short,
                                    )
                                }
                            } else {
                                onEvent(HomeEvent.ShowDeleteTaskListAlertDialog)
                            }
                            onEvent(HomeEvent.DismissHomeMoreDropdown)
                        },
                        taskListName = uiState.taskListSelected?.name ?: defaultTaskListName,
                        tasks = uiState.tasks,
                        selectionMode = uiState.selectionMode,
                        selectedTasks = uiState.selectedTasks,
                        onClearSelectedTasksClick = { onEvent(HomeEvent.ClearSelectedTasks) },
                        onToggleSelectedTasksPinnedValueClick = { onEvent(HomeEvent.ToggleSelectedTasksPinnedValue) },
                        onDeleteSelectedTasksClick = { onEvent(HomeEvent.ShowDeleteTasksAlertDialog) },
                    )
                },
                content = { paddingValues ->
                    if (contentState.deleteTaskAlertDialogState) {
                        DeleteTaskAlertDialog(
                            onDismissRequest = {
                                onEvent(HomeEvent.DismissDeleteTaskAlertDialog)
                            },
                            onDeleteTaskClick = { onEvent(HomeEvent.DeleteTask(contentState.swipedTaskId)) },
                        )
                    }
                    if (contentState.deleteTasksAlertDialogState) {
                        DeleteTasksAlertDialog(
                            onDismissRequest = {
                                onEvent(HomeEvent.DismissDeleteTasksAlertDialog)
                            },
                            onDeleteTaskClick = { onEvent(HomeEvent.DeleteSelectedTasks) },
                        )
                    }
                    if (contentState.deleteTaskListAlertDialogState) {
                        DeleteTaskListAlertDialog(
                            onDismissRequest = { onEvent(HomeEvent.DismissDeleteTaskListAlertDialog) },
                            onDeleteTaskListClick = { onEvent(HomeEvent.DeleteTaskList) },
                        )
                    }
                    if (uiState.isLoadingTasks) {
                        ContentLoadingProgress()
                    } else {
                        TasksListView(
                            tasksDoingPinned = uiState.tasksDoingPinned,
                            tasksDoingNotPinned = uiState.tasksDoingNotPinned,
                            tasksDone = uiState.tasksDone,
                            selectedTasksIds = uiState.selectedTasksIds,
                            onDoingClick = { onEvent(HomeEvent.SetTaskDoing(it)) },
                            onDoneClick = { onEvent(HomeEvent.SetTaskDone(it)) },
                            onTaskItemClick = { taskId ->
                                if (uiState.selectionMode) {
                                    onEvent(HomeEvent.ToggleSelectTask(taskId))
                                } else {
                                    onEvent(HomeNavigationEvent.NavigateToTaskDetails(taskId))
                                }
                            },
                            onTaskItemLongClick = { onEvent(HomeEvent.ToggleSelectTask(it)) },
                            onSwipeToDismiss = {
                                onEvent(HomeEvent.ShowDeleteTaskAlertDialog(it))
                            },
                            selectionMode = uiState.selectionMode,
                            modifier = Modifier.padding(paddingValues),
                        )
                    }
                },
                floatingActionButton = {
                    HomeFloatingActionButton(
                        visible = !uiState.selectionMode,
                        navigateToAddTask = { onEvent(HomeNavigationEvent.NavigateToAddTask) },
                    )
                },
                floatingActionButtonPosition = FabPosition.End,
                snackbarHost = { SnackbarHost(contentState.snackbarHostState) },
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
        tasks: ImmutableList<TaskItem>,
    ) {
        val tonalElevation by animateDpAsState(
            if (selectionMode) HomeTopAppBarTonalElevation else 0.dp,
            animationSpec =
                tween(
                    durationMillis = HOME_TOP_APP_BAR_ANIMATION_DURATION,
                    easing = FastOutSlowInEasing,
                ),
        )
        Surface(tonalElevation = tonalElevation) {
            Column {
                Box {
                    if (selectionMode) {
                        SelectedTasksTopAppBar(
                            onClearSelectedTasksClick = onClearSelectedTasksClick,
                            onToggleSelectedTasksPinnedValueClick = onToggleSelectedTasksPinnedValueClick,
                            onDeleteSelectedTasksClick = onDeleteSelectedTasksClick,
                            selectedTasks = selectedTasks,
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
                                        contentDescription = stringResource(Res.string.menu),
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = onMoreClick) {
                                    Icon(
                                        Images.Icons.MoreVert,
                                        contentDescription = stringResource(Res.string.more),
                                    )
                                }
                                HomeMoreDropdownMenu(
                                    onEditTaskListClick = onEditTaskListClick,
                                    onDeleteTaskListClick = onDeleteTaskListClick,
                                    expanded = homeMoreDropdownExpanded,
                                    onDismissRequest = onHomeMoreDropdownDismissRequest,
                                )
                            },
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
        selectedTasks: ImmutableList<TaskItem>,
    ) {
        val atLeastOneNotPinnedTaskItem = remember(selectedTasks) { selectedTasks.any { !it.isPinned } }
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = onClearSelectedTasksClick) {
                    Icon(
                        Images.Icons.Close,
                        contentDescription = null,
                    )
                }
            },
            title = {
                Text(text = stringResource(Res.string.selected_tasks, selectedTasks.size))
            },
            actions = {
                IconButton(onClick = onToggleSelectedTasksPinnedValueClick) {
                    Icon(
                        imageVector =
                            if (atLeastOneNotPinnedTaskItem) {
                                Images.Icons.PushPin
                            } else {
                                Images.Icons.PushPinFilled
                            },
                        contentDescription =
                            if (atLeastOneNotPinnedTaskItem) {
                                stringResource(Res.string.not_pinned_task)
                            } else {
                                stringResource(Res.string.pinned_task)
                            },
                    )
                }
                IconButton(onClick = onDeleteSelectedTasksClick) {
                    Icon(
                        Images.Icons.Delete,
                        contentDescription = stringResource(Res.string.delete_tasks),
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(),
        )
    }

    @Composable
    private fun HomeFloatingActionButton(
        visible: Boolean,
        navigateToAddTask: () -> Unit,
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut(),
        ) {
            FloatingActionButton(onClick = navigateToAddTask) {
                Icon(
                    Images.Icons.Add,
                    contentDescription = stringResource(Res.string.add_task),
                )
            }
        }
    }

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
        modifier: Modifier = Modifier,
    ) {
        var areTasksDoneVisible by remember { mutableStateOf(false) }
        if ((tasksDoingPinned + tasksDoingNotPinned + tasksDone).isEmpty()) {
            HomeInfoIllustration(
                Images.Illustrations.NoTasks,
                stringResource(Res.string.no_tasks),
            )
        } else {
            LazyColumn(
                modifier =
                    modifier
                        .elementId(HomeElementId.HomeTasksListView),
                contentPadding = PaddingValues(top = 8.dp),
            ) {
                if (tasksDoingPinned.isNotEmpty()) {
                    item {
                        TasksSeparator(stringResource(Res.string.pinned))
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
                    selectedTasksIds = selectedTasksIds,
                )
                if (tasksDoingPinned.isNotEmpty() && tasksDoingNotPinned.isNotEmpty()) {
                    item {
                        TasksSeparator(stringResource(Res.string.others))
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
                    selectedTasksIds = selectedTasksIds,
                )
                if (tasksDone.isNotEmpty()) {
                    item {
                        CompletedTasksHeader(
                            completedTasks = tasksDone.size,
                            enabled = !selectionMode,
                            expanded = areTasksDoneVisible,
                            showExpandIcon = !selectionMode,
                            onClick = { areTasksDoneVisible = !areTasksDoneVisible },
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
                        selectedTasksIds = selectedTasksIds,
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(HomeTaskListAreaBottomPadding))
                }
            }
            if ((tasksDoingPinned + tasksDoingNotPinned).isEmpty() && !areTasksDoneVisible) {
                HomeInfoIllustration(
                    Images.Illustrations.CompletedTasks,
                    stringResource(Res.string.you_have_completed_all_tasks),
                    stringResource(Res.string.congratulations),
                )
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    private fun LazyListScope.taskItems(
        tasks: ImmutableList<TaskItem>,
        onDoingClick: (String) -> Unit,
        onDoneClick: (String) -> Unit,
        onTaskItemClick: (String) -> Unit,
        onTaskItemLongClick: (String) -> Unit,
        onSwipeToDismiss: (String) -> Unit,
        selectionMode: Boolean,
        selectedTasksIds: ImmutableList<String>,
    ) {
        items(
            tasks,
            key = { it.id },
            contentType = { it },
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
                selected = selectedTasksIds.contains(taskItem.id),
            )
        }
    }

    @Composable
    private fun TasksSeparator(text: String) {
        Text(
            text,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge,
            modifier =
                Modifier
                    .padding(horizontal = SectionPadding, vertical = 8.dp),
        )
    }

    @Composable
    private fun CompletedTasksHeader(
        completedTasks: Int,
        enabled: Boolean,
        expanded: Boolean,
        showExpandIcon: Boolean,
        onClick: () -> Unit,
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = stringResource(Res.string.completed_tasks, completedTasks),
                )
            },
            trailingContent = {
                if (showExpandIcon) {
                    if (expanded) {
                        Icon(
                            Images.Icons.ExpandLess,
                            contentDescription = null,
                        )
                    } else {
                        Icon(
                            Images.Icons.ExpandMore,
                            contentDescription = null,
                        )
                    }
                }
            },
            modifier =
                Modifier.clickable(
                    enabled = enabled,
                    onClick = onClick,
                ),
        )
    }

    @Composable
    private fun HomeInfoIllustration(
        imageVector: ImageVector,
        text: String,
        secondaryText: String? = null,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center).padding(bottom = 72.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    imageVector,
                    modifier = Modifier.size(220.dp).padding(bottom = 36.dp),
                    contentDescription = null,
                )
                Text(text = text)
                secondaryText?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.onSurface.applyMediumEmphasisAlpha(),
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }
            }
        }
    }
}

private const val HOME_TOP_APP_BAR_ANIMATION_DURATION: Int = 400
private val HomeTaskListAreaBottomPadding: Dp = 84.dp
private val HomeTopAppBarTonalElevation: Dp = 4.dp
