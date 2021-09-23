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

package com.sergiobelda.todometer.compose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SingleLineItem(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)?,
    text: @Composable (() -> Unit),
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.heightIn(min = 56.dp).fillMaxWidth().clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Box(modifier = Modifier.padding(start = 24.dp)) {
                it()
            }
        }
        Box(modifier = Modifier.padding(start = 24.dp)) {
            text()
        }
    }
}

@Composable
fun TwoLineItem(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)?,
    text: @Composable (() -> Unit),
    subtitle: @Composable (() -> Unit),
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.heightIn(min = 64.dp).fillMaxWidth().clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Box(modifier = Modifier.padding(start = 24.dp)) {
                it()
            }
        }
        Column(modifier = Modifier.padding(start = 24.dp)) {
            text()
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                subtitle()
            }
        }
    }
}
