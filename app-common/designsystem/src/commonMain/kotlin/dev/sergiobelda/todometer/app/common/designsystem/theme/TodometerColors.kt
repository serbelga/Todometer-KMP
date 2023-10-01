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

package dev.sergiobelda.todometer.app.common.designsystem.theme

import androidx.compose.ui.graphics.Color

class TodometerColors(
    val check: Color,
    val amber: Color,
    val blue: Color,
    val brown: Color,
    val gray: Color,
    val green: Color,
    val indigo: Color,
    val lime: Color,
    val orange: Color,
    val red: Color,
    val pink: Color,
    val teal: Color,
    val yellow: Color
)

fun todometerLightColors(
    check: Color = Color.Unspecified,
    amber: Color = Color.Unspecified,
    blue: Color = Color.Unspecified,
    brown: Color = Color.Unspecified,
    gray: Color = Color.Unspecified,
    green: Color = Color.Unspecified,
    indigo: Color = Color.Unspecified,
    lime: Color = Color.Unspecified,
    orange: Color = Color.Unspecified,
    red: Color = Color.Unspecified,
    pink: Color = Color.Unspecified,
    teal: Color = Color.Unspecified,
    yellow: Color = Color.Unspecified
): TodometerColors =
    TodometerColors(
        check = check,
        amber = amber,
        blue = blue,
        brown = brown,
        gray = gray,
        green = green,
        indigo = indigo,
        lime = lime,
        orange = orange,
        red = red,
        pink = pink,
        teal = teal,
        yellow = yellow
    )

fun todometerDarkColors(
    check: Color = Color.Unspecified,
    amber: Color = Color.Unspecified,
    blue: Color = Color.Unspecified,
    brown: Color = Color.Unspecified,
    gray: Color = Color.Unspecified,
    green: Color = Color.Unspecified,
    indigo: Color = Color.Unspecified,
    lime: Color = Color.Unspecified,
    orange: Color = Color.Unspecified,
    red: Color = Color.Unspecified,
    pink: Color = Color.Unspecified,
    teal: Color = Color.Unspecified,
    yellow: Color = Color.Unspecified
): TodometerColors =
    TodometerColors(
        check = check,
        amber = amber,
        blue = blue,
        brown = brown,
        gray = gray,
        green = green,
        indigo = indigo,
        lime = lime,
        orange = orange,
        red = red,
        pink = pink,
        teal = teal,
        yellow = yellow
    )
