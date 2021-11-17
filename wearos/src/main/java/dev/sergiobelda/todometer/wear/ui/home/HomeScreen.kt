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

package dev.sergiobelda.todometer.wear.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.rememberScalingLazyListState
import dev.sergiobelda.todometer.common.model.Project
import dev.sergiobelda.todometer.common.sampledata.sampleProjects
import dev.sergiobelda.todometer.wear.R
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    openProject: (String) -> Unit,
    homeViewModel: HomeViewModel = getViewModel()
) {
    val scalingLazyListState: ScalingLazyListState = rememberScalingLazyListState()
    Scaffold(
        positionIndicator = { PositionIndicator(scalingLazyListState = scalingLazyListState) },
        vignette = {
            Vignette(vignettePosition = VignettePosition.TopAndBottom)
        }
    ) {
        ScalingLazyColumn(
            contentPadding = PaddingValues(
                top = 28.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 40.dp
            ),
            state = scalingLazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(stringResource(R.string.app_name))
            }
            item {
                AddProjectButton()
            }
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }
            if (sampleProjects.isNullOrEmpty()) {
                item {
                    Text(text = stringResource(id = R.string.no_projects))
                }
            } else {
                items(sampleProjects.size) { index ->
                    ProjectItem(sampleProjects[index]) { openProject(it) }
                }
            }
        }
    }
}

@Composable
fun ProjectItem(
    project: Project,
    onClick: (String) -> Unit
) {
    Chip(
        colors = ChipDefaults.secondaryChipColors(),
        label = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.onSurface,
                text = project.name
            )
        },
        onClick = { onClick(project.id) }
    )
}

@Composable
fun AddProjectButton() {
    Chip(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        colors = ChipDefaults.secondaryChipColors(),
        icon = {
            Icon(
                Icons.Rounded.Add,
                contentDescription = "Add"
            )
        },
        label = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Add Project"
            )
        },
        onClick = {
        }
    )
}
