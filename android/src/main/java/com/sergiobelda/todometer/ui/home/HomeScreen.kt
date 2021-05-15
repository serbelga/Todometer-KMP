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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.android.R
import com.sergiobelda.todometer.common.datasource.doIfSuccess
import com.sergiobelda.todometer.common.model.Project
import com.sergiobelda.todometer.common.model.ProjectTasks
import com.sergiobelda.todometer.common.model.Tag
import com.sergiobelda.todometer.common.model.TaskTag
import com.sergiobelda.todometer.common.sampledata.tagsSample
import com.sergiobelda.todometer.compose.mapper.composeColorOf
import com.sergiobelda.todometer.compose.ui.components.DragIndicator
import com.sergiobelda.todometer.compose.ui.components.HorizontalDivider
import com.sergiobelda.todometer.compose.ui.task.TaskItem
import com.sergiobelda.todometer.compose.ui.theme.TodometerColors
import com.sergiobelda.todometer.compose.ui.theme.TodometerTypography
import com.sergiobelda.todometer.compose.ui.theme.primarySelected
import com.sergiobelda.todometer.ui.components.ToDometerTopAppBar
import com.sergiobelda.todometer.ui.theme.ToDometerTheme
import com.sergiobelda.todometer.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    addProject: () -> Unit,
    addTask: () -> Unit,
    openTask: (Long) -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val selectedTask = remember { mutableStateOf(0L) }
    val deleteTaskAlertDialogState = remember { mutableStateOf(false) }

    var projects: List<Project> by remember { mutableStateOf(emptyList()) }
    val projectsResultState = mainViewModel.projects.observeAsState()
    projectsResultState.value?.let { result ->
        result.doIfSuccess { projects = it }
    }

    var projectSelected: ProjectTasks? by remember { mutableStateOf(null) }
    val projectSelectedState = mainViewModel.projectSelected.observeAsState()
    projectSelectedState.value?.let { result ->
        result.doIfSuccess { projectSelected = it }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetElevation = 16.dp,
        sheetContent = {
            SheetContainer(
                projectSelected?.id,
                projects,
                addProject,
                selectProject = {
                    mainViewModel.setProjectSelected(it)
                },
                tagsSample
            )
        }
    ) {
        Scaffold(
            topBar = {
                ToDometerTopAppBar(projectSelected)
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
                                    scope.launch { sheetState.show() }
                                }
                            ) {
                                Icon(Icons.Rounded.Menu, contentDescription = "Menu")
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = { /* doSomething() */ }) {
                                Icon(Icons.Rounded.MoreVert, contentDescription = "More")
                            }
                        }
                    }
                }
            },
            content = {
                if (deleteTaskAlertDialogState.value) {
                    RemoveTaskAlertDialog(
                        deleteTaskAlertDialogState,
                        deleteTask = { mainViewModel.deleteTask(selectedTask.value) }
                    )
                }
                /*
                if (!projects.value.isNullOrEmpty()) {
                    ProjectTasksListView(
                        mainViewModel,
                        onTaskItemClick = openTask,
                        onTaskItemLongClick = {
                            deleteTaskAlertDialogState.value = true
                            selectedTask.value = it
                        }
                    )
                } else {
                    EmptyProjectTaskListView(addProject)
                }

                 */
                // TODO: 02/04/2021 Update null check
                TasksListView(
                    projectSelected?.tasks ?: emptyList(),
                    onDoingClick = {
                        mainViewModel.setTaskDoing(it)
                    },
                    onDoneClick = {
                        mainViewModel.setTaskDone(it)
                    },
                    onTaskItemClick = openTask,
                    onTaskItemLongClick = {
                        deleteTaskAlertDialogState.value = true
                        selectedTask.value = it
                    }
                )
            },
            floatingActionButton = {
                if (!projects.isNullOrEmpty()) {
                    FloatingActionButton(
                        onClick = addTask
                    ) {
                        Icon(Icons.Rounded.Add, contentDescription = "Add task")
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true
        )
    }
}

@Composable
fun RemoveTaskAlertDialog(
    showRemoveTaskAlertDialog: MutableState<Boolean>,
    deleteTask: () -> Unit
) {
    AlertDialog(
        title = {
            Text(stringResource(id = R.string.remove_task))
        },
        onDismissRequest = {
            showRemoveTaskAlertDialog.value = false
        },
        text = {
            Text(stringResource(id = R.string.remove_task_question))
        },
        confirmButton = {
            TextButton(
                onClick = {
                    deleteTask()
                    showRemoveTaskAlertDialog.value = false
                }
            ) {
                Text(stringResource(id = R.string.ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    showRemoveTaskAlertDialog.value = false
                }
            ) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}

@Composable
fun SheetContainer(
    selectedProjectId: Long?,
    projectList: List<Project>,
    addProject: () -> Unit,
    selectProject: (Long) -> Unit,
    tagList: List<Tag>
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
                text = stringResource(id = R.string.projects).toUpperCase(Locale.ROOT),
                style = typography.overline
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        HorizontalDivider()
        LazyColumn {
            items(projectList) { project ->
                ProjectListItem(project, project.id == selectedProjectId, selectProject)
            }
        }
        TextButton(onClick = addProject, modifier = Modifier.fillMaxWidth()) {
            Icon(Icons.Rounded.Add, contentDescription = "Add project")
            Text(text = stringResource(id = R.string.add_project))
        }
        HorizontalDivider()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(56.dp)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.tags).toUpperCase(Locale.ROOT),
                style = typography.overline
            )
            Spacer(modifier = Modifier.weight(1f))

        }
        HorizontalDivider()
        LazyColumn {
            items(tagList) { tag ->
                TagItem(tag)
            }
        }
        TextButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Icon(Icons.Rounded.Add, contentDescription = "Add tag")
            Text(text = stringResource(id = R.string.add_tag))
        }
    }
}

@Composable
fun ProjectListItem(
    project: Project,
    selected: Boolean,
    onItemClick: (Long) -> Unit
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
        Icon(
            Icons.Default.Book,
            tint = selectedColor,
            contentDescription = null,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = project.name,
            color = selectedColor,
            style = TodometerTypography.subtitle2,
            modifier = Modifier.weight(1f).padding(start = 16.dp, end = 16.dp)
        )
    }
}

@Composable
fun TagItem(tag: Tag) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(56.dp).clickable(onClick = { })
    ) {
        Box(
            modifier = Modifier
                .padding(start = 16.dp)
                .size(16.dp)
                .clip(CircleShape)
                .background(TodometerColors.composeColorOf(tag.color))
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = tag.name,
                style = TodometerTypography.subtitle2,
                modifier = Modifier.padding(start = 16.dp).weight(1f)
            )
        }
    }
}

@Composable
fun TasksListView(
    tasks: List<TaskTag>,
    onDoingClick: (Long) -> Unit,
    onDoneClick: (Long) -> Unit,
    onTaskItemClick: (Long) -> Unit,
    onTaskItemLongClick: (Long) -> Unit
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
fun EmptyProjectTaskListView(addProject: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(id = R.drawable.project_completed),
                modifier = Modifier.size(240.dp),
                contentDescription = null
            )
            Text(stringResource(id = R.string.you_have_not_any_project))
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                onClick = addProject
            ) {
                Text(stringResource(id = R.string.add_project))
            }
        }
    }
}

@Preview
@Composable
fun EmptyProjectTaskListPreview() {
    ToDometerTheme {
        EmptyProjectTaskListView(addProject = {})
    }
}
