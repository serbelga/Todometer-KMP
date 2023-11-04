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

package dev.sergiobelda.todometer.app.common.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.designsystem.resources.images.Images
import dev.sergiobelda.todometer.common.designsystem.resources.images.symbols.IsotypeCutMonochrome
import dev.sergiobelda.todometer.common.resources.TodometerResources

@Composable
fun TodometerTitle(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(TodometerTitleTextPaddingStart)
    ) {
        Icon(
            Images.Symbols.IsotypeCutMonochrome,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(TodometerTitleIconSize)
        )
        Text(
            text = TodometerResources.strings.appName,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(
                bottom = TodometerTitleTextPaddingBottom
            ),
            fontWeight = FontWeight.Bold
        )
    }
}

private val TodometerTitleIconSize: Dp = 24.dp
private val TodometerTitleTextPaddingStart: Dp = 8.dp
private val TodometerTitleTextPaddingBottom: Dp = 4.dp
