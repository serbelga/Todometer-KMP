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

package dev.sergiobelda.todometer.common.compose.ui.designsystem.mapper

import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.ToDometerColors
import dev.sergiobelda.todometer.common.domain.model.Tag

typealias ComposeColor = androidx.compose.ui.graphics.Color

@Composable
fun ToDometerColors.composeColorOf(tag: Tag): ComposeColor {
    return when (tag) {
        Tag.PINK -> pink
        Tag.RED -> red
        Tag.INDIGO -> indigo
        Tag.BLUE -> blue
        Tag.TEAL -> teal
        Tag.GREEN -> green
        Tag.LIME -> lime
        Tag.YELLOW -> yellow
        Tag.AMBER -> amber
        Tag.ORANGE -> orange
        Tag.BROWN -> brown
        Tag.GRAY -> gray
        else -> gray
    }
}
