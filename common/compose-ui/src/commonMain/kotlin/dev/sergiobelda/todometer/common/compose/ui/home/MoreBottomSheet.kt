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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.HorizontalDivider
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.Alpha
import dev.sergiobelda.todometer.common.compose.ui.preferences.themeIcon
import dev.sergiobelda.todometer.common.compose.ui.preferences.themeName
import dev.sergiobelda.todometer.common.domain.preference.AppTheme
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.stringResource

@Composable
fun MoreBottomSheet(
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
                    stringResource(resource = MR.strings.open_source_licenses),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Text("·")
            TextButton(onClick = aboutClick) {
                Text(
                    stringResource(resource = MR.strings.about),
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
                stringResource(resource = MR.strings.edit_task_list),
                style = MaterialTheme.typography.titleSmall
            )
        },
        supportingText = {
            if (!editTaskListEnabled) {
                Text(
                    stringResource(resource = MR.strings.cannot_edit_this_task_list),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        leadingContent = {
            Icon(
                Icons.Outlined.Edit,
                contentDescription = stringResource(resource = MR.strings.edit_task_list)
            )
        },
        modifier = Modifier.clickable(
            enabled = editTaskListEnabled,
            onClick = editTaskListClick
        ).height(MoreBottomSheetListItemHeight)
            .alpha(if (editTaskListEnabled) Alpha.High else Alpha.Disabled)
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
                stringResource(resource = MR.strings.delete_task_list),
                style = MaterialTheme.typography.titleSmall
            )
        },
        supportingText = {
            if (!deleteTaskListEnabled) {
                Text(
                    stringResource(resource = MR.strings.cannot_delete_this_task_list),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        leadingContent = {
            Icon(
                Icons.Outlined.Delete,
                contentDescription = stringResource(resource = MR.strings.delete_task_list)
            )
        },
        modifier = Modifier.clickable(
            enabled = deleteTaskListEnabled,
            onClick = deleteTaskListClick
        ).height(MoreBottomSheetListItemHeight)
            .alpha(if (deleteTaskListEnabled) Alpha.High else Alpha.Disabled)
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
                stringResource(resource = MR.strings.theme),
                style = MaterialTheme.typography.titleSmall
            )
        },
        supportingText = {
            Text(
                currentTheme.themeName(),
                style = MaterialTheme.typography.labelLarge
            )
        },
        leadingContent = {
            Icon(
                currentTheme.themeIcon(),
                contentDescription = currentTheme.themeName()
            )
        },
        modifier = Modifier.height(MoreBottomSheetListItemHeight)
            .clickable(onClick = chooseThemeClick)
    )
}

private val MoreBottomSheetListItemHeight = 64.dp
