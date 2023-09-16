/*
 * Copyright 2023 Sergio Belda
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

package dev.sergiobelda.todometer.common.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import dev.icerock.moko.resources.compose.painterResource

object ToDometerSymbols {
    val IsotypeDark: Painter
        @Composable
        get() = painterResource(MR.images.isotype_cut_dark_48)

    val IsotypeLight: Painter
        @Composable
        get() = painterResource(MR.images.isotype_cut_light_48)

    val IsotypeMonochrome: Painter
        @Composable
        get() = painterResource(MR.images.isotype_cut_monochrome_48)
}
