/*
 * Copyright 2023 Sergio Belda
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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.components.ToDometerTitle
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.ToDometerDivider
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.ToDometerTheme
import dev.sergiobelda.todometer.common.domain.model.TaskList
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.ToDometerIcons
import dev.sergiobelda.todometer.common.resources.painterResource
import dev.sergiobelda.todometer.common.resources.stringResource

@Composable
internal fun HomeDrawerContent(
    selectedTaskListId: String,
    defaultTaskListName: String,
    taskLists: List<TaskList>,
    onAddTaskList: () -> Unit,
    onTaskListItemClick: (String) -> Unit,
    onSettingsItemClick: () -> Unit,
    onAboutItemClick: () -> Unit
) {
    Box(
        modifier = Modifier.height(HomeDrawerTopHeight).fillMaxWidth()
    ) {
        ToDometerTitle(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = HomeDrawerTopPaddingHorizontal)
        )
    }
    ToDometerDivider()
    HomeDrawerTaskListsSection(
        selectedTaskListId,
        defaultTaskListName,
        taskLists,
        onAddTaskList,
        onTaskListItemClick
    )
    ToDometerDivider()
    Column(modifier = Modifier.padding(HomeDrawerItemPadding)) {
        HomeNavigationDrawerSettingsItem(onSettingsItemClick)
        HomeNavigationDrawerAboutItem(onAboutItemClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeNavigationDrawerSettingsItem(onClick: () -> Unit) {
    NavigationDrawerItem(
        icon = {
            Icon(
                painterResource(ToDometerIcons.Settings),
                contentDescription = null
            )
        },
        label = {
            Text(
                text = stringResource(MR.strings.settings),
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1
            )
        },
        onClick = onClick,
        selected = false
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeNavigationDrawerAboutItem(onClick: () -> Unit) {
    NavigationDrawerItem(
        icon = {
            Icon(
                painterResource(ToDometerIcons.Info),
                contentDescription = null
            )
        },
        label = {
            Text(
                text = stringResource(MR.strings.about),
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1
            )
        },
        onClick = onClick,
        selected = false
    )
}

@Composable
private fun HomeDrawerTaskListsSection(
    selectedTaskListId: String,
    defaultTaskListName: String,
    taskLists: List<TaskList>,
    onAddTaskList: () -> Unit,
    onTaskListItemClick: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(HomeDrawerSectionTitleHeight)
            .padding(horizontal = HomeDrawerSectionTitlePaddingHorizontal)
    ) {
        Text(
            text = stringResource(MR.strings.task_lists),
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.weight(1f))
        TextButton(onClick = onAddTaskList) {
            Text(stringResource(MR.strings.add_task_list))
        }
    }
    LazyColumn(modifier = Modifier.padding(HomeDrawerItemPadding)) {
        item {
            HomeNavigationDrawerTaskListItem(defaultTaskListName, selectedTaskListId == "") {
                onTaskListItemClick("")
            }
        }
        items(taskLists) { taskList ->
            HomeNavigationDrawerTaskListItem(taskList.name, taskList.id == selectedTaskListId) {
                onTaskListItemClick(taskList.id)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeNavigationDrawerTaskListItem(
    text: String,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    val colors = NavigationDrawerItemDefaults.colors(
        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
        unselectedTextColor = ToDometerTheme.toDometerColors.onSurfaceMediumEmphasis
    )
    NavigationDrawerItem(
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1
            )
        },
        onClick = onItemClick,
        selected = isSelected,
        colors = colors
    )
}

private val HomeDrawerTopHeight: Dp = 56.dp
private val HomeDrawerTopPaddingHorizontal: Dp = 16.dp
private val HomeDrawerSectionTitleHeight: Dp = 56.dp
private val HomeDrawerSectionTitlePaddingHorizontal: Dp = 16.dp
private val HomeDrawerItemPadding: Dp = 8.dp
