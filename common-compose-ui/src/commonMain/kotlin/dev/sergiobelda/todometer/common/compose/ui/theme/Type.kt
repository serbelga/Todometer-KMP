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

package dev.sergiobelda.todometer.common.compose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

expect val quickSandFontFamily: FontFamily

object Type {
    val typography = Typography(
        h4 = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 34.sp
        ),
        h5 = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
        ),
        h6 = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            letterSpacing = 0.15.sp
        ),
        subtitle1 = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        ),
        subtitle2 = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        ),
        body1 = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            letterSpacing = 0.5.sp
        ),
        body2 = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            letterSpacing = 0.25.sp
        ),
        button = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        ),
        caption = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            letterSpacing = 0.4.sp
        ),
        overline = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            letterSpacing = 1.5.sp
        )
    )
}
