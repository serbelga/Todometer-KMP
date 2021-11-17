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

package dev.sergiobelda.todometer.wear.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors

val primary = Color(0xFF80b4ff)
val green = Color(0xFF3DDC84)
val navy = Color(0xFF073042)
val navyDark = Color(0xFF00071c)
val orange = Color(0xFFF86734)

val colorPalette = Colors(
    primary = primary,
    secondary = green,
    surface = navy,
    background = navyDark
)
