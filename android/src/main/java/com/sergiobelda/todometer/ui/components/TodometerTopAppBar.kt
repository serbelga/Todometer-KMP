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

package com.sergiobelda.todometer.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.common.model.Project
import com.sergiobelda.todometer.compose.ui.components.HorizontalDivider
import com.sergiobelda.todometer.compose.ui.theme.MaterialTypography
import com.sergiobelda.todometer.util.ProgressUtil.getPercentage
import com.sergiobelda.todometer.util.ProgressUtil.getTasksDoneProgress

@Composable
fun ToDometerTopAppBar(
    selectedProject: Project?
) {
    Surface(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Column {
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
            ProjectProgress(selectedProject)
            HorizontalDivider()
        }
    }
}

@Composable
fun ProjectProgress(
    selectedProject: Project?
) {
    val progress = getTasksDoneProgress(selectedProject?.tasks ?: emptyList())
    Column(
        modifier = Modifier.fillMaxWidth().requiredWidthIn(max = 360.dp)
            .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
    ) {
        // TODO: 02/04/2021 Max lines
        Text(
            selectedProject?.name?.toUpperCase() ?: "-",
            style = MaterialTypography.overline
        )
        Text(
            text = getPercentage(progress),
            style = MaterialTypography.body2
        )
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth().paddingFromBaseline(8.dp)
        )
    }
}
