/*
 * Copyright 2022 Sergio Belda
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

package dev.sergiobelda.todometer.app.common.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TodometerChip(
    modifier: Modifier = Modifier,
    border: BorderStroke = TodometerChipDefaults.borderStroke,
    color: Color = TodometerChipDefaults.color,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        border = border,
        shape = RoundedCornerShape(TodometerChipCornerRadius),
        color = color
    ) {
        Row(
            modifier = Modifier.padding(TodometerChipContentPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}

private object TodometerChipDefaults {
    val borderStroke: BorderStroke
        @Composable get() = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outline
        )

    val color: Color @Composable get() = MaterialTheme.colorScheme.surface
}

private val TodometerChipContentPadding: PaddingValues = PaddingValues(6.dp)
private val TodometerChipCornerRadius: Dp = 14.dp
