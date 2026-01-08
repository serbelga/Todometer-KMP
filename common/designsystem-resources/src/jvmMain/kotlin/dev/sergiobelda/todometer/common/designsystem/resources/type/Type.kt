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

package dev.sergiobelda.todometer.common.designsystem.resources.type

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

internal val quickSandRegularFont =
    Font(
        resource = "quicksand_regular.ttf",
        weight = FontWeight.Normal,
    )
internal val quickSandMediumFont =
    Font(
        resource = "quicksand_medium.ttf",
        weight = FontWeight.Medium,
    )

actual val quickSandFontFamily =
    FontFamily(
        quickSandRegularFont,
        quickSandMediumFont,
    )
