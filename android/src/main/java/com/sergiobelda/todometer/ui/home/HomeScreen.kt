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
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ListItem
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.android.R
import com.sergiobelda.todometer.common.Greeting
import com.sergiobelda.todometer.compose.ui.theme.MaterialColors
import com.sergiobelda.todometer.model.Project
import com.sergiobelda.todometer.model.TaskState
import com.sergiobelda.todometer.ui.components.DragIndicator
import com.sergiobelda.todometer.ui.components.HorizontalDivider
import com.sergiobelda.todometer.ui.components.ToDometerTitle
import com.sergiobelda.todometer.ui.task.TaskItem
import com.sergiobelda.todometer.ui.theme.ToDometerTheme
import com.sergiobelda.todometer.ui.utils.ProgressUtil
import com.sergiobelda.todometer.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    addProject: () -> Unit,
    addTask: () -> Unit,
    openProject: (Int) -> Unit,
    openTask: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val selectedTask = remember { mutableStateOf(0) }
    val deleteTaskAlertDialogState = remember { mutableStateOf(false) }
    val projectList = mainViewModel.projectList
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetElevation = 16.dp,
        sheetContent = {
            SheetContainer(projectList, addProject, openProject)
        }
    ) {
        Scaffold(
            topBar = {
                ToDometerTopBar()
            },
            bottomBar = {
                if (projectList.isNotEmpty()) {
                    BottomAppBar(
                        backgroundColor = MaterialColors.surface,
                        contentColor = contentColorFor(MaterialColors.surface),
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
                LazyColumn {
                    items(mainViewModel.tasks) {
                        Text(it.title)
                    }
                }
                if (!projectList.isNullOrEmpty()) {
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
            },
            floatingActionButton = {
                if (!projectList.isNullOrEmpty()) {
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
fun ToDometerTopBar() {
    Surface(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
            ) {
                ToDometerTitle(modifier = Modifier.align(Alignment.Center))
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    IconButton(onClick = {}, modifier = Modifier.align(Alignment.CenterEnd)) {
                        Icon(Icons.Outlined.AccountCircle, contentDescription = "Account")
                    }
                }
            }
            HorizontalDivider()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetContainer(projectList: List<Project>, addProject: () -> Unit, openProject: (Int) -> Unit) {
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
            TextButton(onClick = addProject) {
                Icon(Icons.Rounded.Add, contentDescription = "Add project")
                Text(text = stringResource(id = R.string.add_project))
            }
        }
        HorizontalDivider()
        LazyColumn(
            modifier = Modifier.height(240.dp)
        ) {
            items(projectList) { project ->
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    ListItem(
                        modifier = Modifier.clickable(
                            onClick = {
                                openProject(project.id)
                            }
                        ),
                        text = { Text(text = project.name) },
                        icon = {
                            Icon(
                                Icons.Default.Book,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
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
            TextButton(onClick = {}) {
                Icon(Icons.Rounded.Add, contentDescription = "Add tag")
                Text(text = stringResource(id = R.string.add_tag))
            }
        }
        HorizontalDivider()
    }
}

@Composable
fun ProjectTasksListView(
    mainViewModel: MainViewModel,
    onTaskItemClick: (Int) -> Unit,
    onTaskItemLongClick: (Int) -> Unit
) {
    /*
    val projectTasksList = mainViewModel.projectList
    LazyColumn(
        modifier = Modifier.padding(32.dp)
    ) {
        itemsIndexed(projectTasksList) { index, project ->
            Text(project.name.toUpperCase(Locale.ROOT), style = typography.overline)
            val progress =
                if (project.tasks.isNotEmpty()) {
                    project.tasks.filter { it.state == TaskState.DONE }.size.toFloat() / project.tasks.size.toFloat()
                } else {
                    0f
                }
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    ProgressUtil.getPercentage(progress),
                    style = typography.body1,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 16.dp)
                    .fillMaxWidth()
            )
            project.tasks.sortedBy { it.state == TaskState.DONE }.forEach { task ->
                TaskItem(
                    task,
                    updateState = mainViewModel.updateTaskState,
                    onClick = onTaskItemClick,
                    onLongClick = onTaskItemLongClick
                )
            }
            if (index == projectTasksList.size - 1) {
                Spacer(modifier = Modifier.height(32.dp))
            } else {
                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

     */
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
