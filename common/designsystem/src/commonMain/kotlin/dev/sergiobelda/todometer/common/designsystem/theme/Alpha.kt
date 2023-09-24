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

package dev.sergiobelda.todometer.common.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object Alpha {
    const val High: Float = 1f
    const val Medium: Float = 0.6f
    const val Disabled: Float = 0.38f

    @Composable
    fun Color.applyHighEmphasisAlpha(): Color = withAlpha(High)

    @Composable
    fun Color.applyMediumEmphasisAlpha(): Color = withAlpha(Medium)

    @Composable
    fun Color.applyDisabledEmphasisAlpha(): Color = withAlpha(Disabled)
}

@Composable
fun Color.withAlpha(alpha: Float): Color = copy(alpha = alpha)
