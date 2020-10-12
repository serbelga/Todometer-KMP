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
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.Card
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FabPosition
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.R
import com.sergiobelda.todometer.model.Task
import com.sergiobelda.todometer.ui.theme.shapes
import com.sergiobelda.todometer.ui.theme.typography
import com.sergiobelda.todometer.viewmodel.MainViewModel

@Composable
fun Home(
    mainViewModel: MainViewModel,
    addTask: () -> Unit
) {
    Scaffold(
        topBar = {
            ToDometerTopBar()
        },
        floatingActionButton = { AddTaskExtendedFAB(addTask = addTask) },
        floatingActionButtonPosition = FabPosition.Center,
        bodyContent = { HomeBodyContent(mainViewModel.tasks) }
    )
}

@Composable
fun ToDometerTopBar() {
    Surface(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        elevation = 4.dp
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
            Text(
                "20%",
                style = typography.body1,
                modifier = Modifier.padding(top = 4.dp)
            )
            LinearProgressIndicator(
                progress = 0.2f,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )
        }
    }
}

@Composable
fun ToDometerTitle(modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            asset = vectorResource(id = R.drawable.isotype)
        )
        Text(
            text = stringResource(id = R.string.app_name),
            style = typography.h6,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
fun AddTaskExtendedFAB(addTask: () -> Unit) {
    ExtendedFloatingActionButton(
        icon = { Icon(Icons.Rounded.Add) },
        text = { Text(stringResource(id = R.string.add_task)) },
        onClick = addTask
    )
}

@Composable
fun HomeBodyContent(
    tasks: List<Task>
) {
    LazyColumnForIndexed(items = tasks) { index, task ->
        TaskItem(task)
        if (index == tasks.size - 1) {
            Spacer(modifier = Modifier.height(72.dp))
        }
    }
}

@Composable
fun TaskItem(task: Task) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 120.dp)
            .padding(8.dp),
        shape = shapes.large
    ) {
        Row(
            modifier = Modifier.clickable(onClick = {})
        ) {
            Text(task.body)
        }
    }
}
