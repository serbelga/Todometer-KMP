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

package ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.components.TaskListProgress
import dev.sergiobelda.todometer.common.compose.ui.task.TaskItem
import dev.sergiobelda.todometer.common.compose.ui.tasklist.TaskListItem
import dev.sergiobelda.todometer.common.compose.ui.theme.TodometerColors
import dev.sergiobelda.todometer.common.data.doIfSuccess
import dev.sergiobelda.todometer.common.model.Tag
import dev.sergiobelda.todometer.common.model.Task
import dev.sergiobelda.todometer.common.model.TaskList
import dev.sergiobelda.todometer.common.usecase.GetTaskListSelectedTasksUseCase
import dev.sergiobelda.todometer.common.usecase.GetTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.usecase.GetTaskListsUseCase
import dev.sergiobelda.todometer.common.usecase.InsertTaskInTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.usecase.InsertTaskListUseCase
import dev.sergiobelda.todometer.common.usecase.SetTaskDoingUseCase
import dev.sergiobelda.todometer.common.usecase.SetTaskDoneUseCase
import dev.sergiobelda.todometer.common.usecase.SetTaskListSelectedUseCase
import koin
import kotlinx.coroutines.launch
import ui.icons.iconToDometer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var addTaskListAlertDialogState by remember { mutableStateOf(false) }
    var addTaskAlertDialogState by remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val setTaskDoingUseCase = koin.get<SetTaskDoingUseCase>()
    val setTaskDoneUseCase = koin.get<SetTaskDoneUseCase>()
    val getTaskListSelectedUseCase = koin.get<GetTaskListSelectedUseCase>()
    val setTaskListSelectedUseCase = koin.get<SetTaskListSelectedUseCase>()
    val getTaskListSelectedTasksUseCase = koin.get<GetTaskListSelectedTasksUseCase>()
    val getTaskListsUseCase = koin.get<GetTaskListsUseCase>()
    val insertTaskListUseCase = koin.get<InsertTaskListUseCase>()
    val insertTaskInTaskListSelectedUseCase = koin.get<InsertTaskInTaskListSelectedUseCase>()

    var taskListSelected: TaskList? by remember { mutableStateOf(null) }
    val taskListResultState by getTaskListSelectedUseCase().collectAsState(null)
    taskListResultState?.doIfSuccess { taskListSelected = it }

    var tasks: List<Task> by remember { mutableStateOf(emptyList()) }
    val tasksResultState by getTaskListSelectedTasksUseCase().collectAsState(null)
    tasksResultState?.doIfSuccess { tasks = it }

    var taskLists: List<TaskList> by remember { mutableStateOf(emptyList()) }
    val taskListsResultState by getTaskListsUseCase().collectAsState(null)
    taskListsResultState?.doIfSuccess { taskLists = it }

    Scaffold(
        /*
        drawerShape = RoundedCornerShape(0),
        drawerContent = {
            DrawerContainer(addTaskList = { addTaskListAlertDialogState = true })
        },
        */
        topBar = {
            SmallTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = iconToDometer(),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp).padding(end = 8.dp)
                        )
                        Text(text = "ToDometer")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.TwoTone.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            if (taskLists.isNotEmpty()) {
                ExtendedFloatingActionButton(
                    icon = {
                        Icon(Icons.Rounded.Add, "Add task")
                    },
                    text = {
                        Text("Add task")
                    },
                    onClick = { addTaskAlertDialogState = true },
                    backgroundColor = TodometerColors.primary
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        scaffoldState = scaffoldState
    ) {
        Divider()
        Column(modifier = Modifier.fillMaxSize()) {
            if (addTaskListAlertDialogState) {
                AddTaskListAlertDialog(
                    onDismissRequest = { addTaskListAlertDialogState = false }
                ) { taskListName ->
                    coroutineScope.launch {
                        insertTaskListUseCase.invoke(taskListName)
                    }
                }
            }
            if (addTaskAlertDialogState) {
                AddTaskAlertDialog(
                    onDismissRequest = { addTaskAlertDialogState = false }
                ) { title, description, _ ->
                    coroutineScope.launch {
                        insertTaskInTaskListSelectedUseCase.invoke(title, description, Tag.GRAY)
                    }
                }
            }
        }
        if (taskLists.isNotEmpty()) {
            Row {
                TaskListsNavigationDrawer(
                    taskLists,
                    taskListSelected,
                    onTaskListClick = {
                        coroutineScope.launch {
                            setTaskListSelectedUseCase.invoke(it)
                        }
                    },
                    onAddTaskListClick = { addTaskListAlertDialogState = true }
                )
                Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                Column {
                    TaskListProgress(taskListSelected, tasks)
                    if (tasks.isEmpty()) {
                        EmptyTasksListView()
                    } else {
                        TasksListView(
                            tasks,
                            onDoingClick = {
                                coroutineScope.launch {
                                    setTaskDoingUseCase(it)
                                }
                            },
                            onDoneClick = {
                                coroutineScope.launch {
                                    setTaskDoneUseCase(it)
                                }
                            },
                            onTaskItemClick = {},
                            onTaskItemLongClick = {}
                        )
                    }
                }
            }
        } else {
            EmptyTaskListsView(addTaskList = { addTaskListAlertDialogState = true })
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
                painterResource("images/no_task_lists.svg"),
                modifier = Modifier.size(240.dp).padding(bottom = 24.dp),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text("There's any task list", modifier = Modifier.padding(bottom = 48.dp))
            Button(onClick = addTaskList) {
                Text("Add task list")
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
        items(tasks) {
            TaskItem(
                task = it,
                onDoingClick = onDoingClick,
                onDoneClick = onDoneClick,
                onClick = onTaskItemClick,
                onLongClick = onTaskItemLongClick
            )
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
                painterResource("images/no_tasks.svg"),
                modifier = Modifier.size(240.dp).padding(bottom = 24.dp),
                contentDescription = null
            )
            Text("There are any task")
        }
    }
}

@Composable
fun TaskListsNavigationDrawer(
    taskLists: List<TaskList>,
    taskListSelected: TaskList?,
    onTaskListClick: (taskListId: String) -> Unit,
    onAddTaskListClick: () -> Unit
) {
    Column(modifier = Modifier.requiredWidth(280.dp).padding(top = 16.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "TASK LISTS",
                style = MaterialTheme.typography.labelSmall
            )
            OutlinedButton(
                onClick = onAddTaskListClick,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Icon(Icons.Rounded.Add, contentDescription = "Add task list")
                Text(text = "Add task list")
            }
        }
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(taskLists) { taskList ->
                TaskListItem(
                    taskList.name,
                    taskList.id == taskListSelected?.id
                ) {
                    onTaskListClick(taskList.id)
                }
            }
        }
    }
}

@Composable
fun DrawerContainer(addTaskList: () -> Unit) {
    OutlinedButton(onClick = addTaskList) {
        Icon(Icons.Rounded.Add, contentDescription = "Add task list")
        Text(text = "Add task list")
    }
}
