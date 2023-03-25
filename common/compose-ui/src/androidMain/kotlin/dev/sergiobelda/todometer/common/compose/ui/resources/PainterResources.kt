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

package dev.sergiobelda.todometer.common.compose.ui.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import dev.sergiobelda.todometer.common.compose.ui.R

actual object PainterResources {
    @Composable
    actual fun iconToDometer(): Painter = painterResource(id = R.drawable.isotype_monochrome)

    @Composable
    actual fun iconDelete(): Painter =
        painterResource(id = R.drawable.ic_baseline_delete_24)

    @Composable
    actual fun completedTasks(): Painter =
        painterResource(id = R.drawable.completed_tasks)

    @Composable
    actual fun noTasks(): Painter =
        painterResource(id = R.drawable.no_tasks)
}
