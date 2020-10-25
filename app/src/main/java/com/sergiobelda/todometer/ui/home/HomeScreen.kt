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

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.EmphasisAmbient
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.ProvideEmphasis
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.model.Task
import com.sergiobelda.todometer.model.TaskState
import com.sergiobelda.todometer.ui.components.ToDometerTitle
import com.sergiobelda.todometer.ui.utils.ProgressUtil
import com.sergiobelda.todometer.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    addTask: () -> Unit,
    openTask: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            ToDometerTopBar(mainViewModel.tasks)
        },
        bottomBar = {
            BottomAppBar(
                backgroundColor = colors.surface,
                contentColor = colors.onSurface,
                cutoutShape = CircleShape
            ) {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Rounded.Menu)
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Rounded.MoreVert)
                }
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                icon = { Icon(Icons.Rounded.Add) },
                onClick = addTask
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        bodyContent = { HomeBodyContent(mainViewModel, openTask) }
    )
}

@Composable
fun ToDometerTopBar(tasks: List<Task>) {
    val progress =
        if (tasks.isNotEmpty()) {
            tasks.filter { it.state == TaskState.DONE }.size.toFloat() / tasks.size.toFloat()
        } else {
            0f
        }
    Surface(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.height(56.dp).fillMaxWidth()
            ) {
                ToDometerTitle(modifier = Modifier.align(Alignment.Center))
                IconButton(onClick = {}, modifier = Modifier.align(Alignment.CenterEnd)) {
                    Icon(Icons.Outlined.AccountCircle)
                }
            }
            ProvideEmphasis(emphasis = EmphasisAmbient.current.medium) {
                Text(
                    ProgressUtil.getPercentage(progress),
                    style = typography.body1,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )
        }
    }
}

@Composable
fun HomeBodyContent(
    mainViewModel: MainViewModel,
    openTask: (Int) -> Unit
) {
    val tasks = mainViewModel.tasks
    LazyColumnForIndexed(items = tasks) { index, task ->
        TaskItem(
            task,
            updateState = mainViewModel.updateTaskState,
            onClick = openTask
        )
        if (index == tasks.size - 1) {
            Spacer(modifier = Modifier.height(72.dp))
        }
    }
}
