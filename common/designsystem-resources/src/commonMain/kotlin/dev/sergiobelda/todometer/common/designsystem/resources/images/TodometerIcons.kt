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

package dev.sergiobelda.todometer.common.designsystem.resources.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
object TodometerIcons {

    private const val IconsPath = "images-xml/icons/"
    private const val IconExtension = ".xml"

    val Add: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_add_24${IconExtension}")

    val ArrowBack: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_arrow_back_24${IconExtension}")

    val Check: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_check_24${IconExtension}")

    val CheckBox: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_check_box_24${IconExtension}")

    val CheckCircle: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_check_circle_24${IconExtension}")

    val Close: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_close_24${IconExtension}")

    val Code: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_code_24${IconExtension}")

    val Contrast: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_contrast_24${IconExtension}")

    val DarkMode: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_dark_mode_24${IconExtension}")

    val Delete: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_delete_24${IconExtension}")

    val Description: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_description_24${IconExtension}")

    val Edit: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_edit_24${IconExtension}")

    val Event: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_event_24${IconExtension}")

    val ExpandLess: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_expand_less_24${IconExtension}")

    val ExpandMore: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_expand_more_24${IconExtension}")

    val GitHub: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_github_24${IconExtension}")

    val Info: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_info_24${IconExtension}")

    val LightMode: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_light_mode_24${IconExtension}")

    val Menu: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_menu_24${IconExtension}")

    val MoreVert: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_more_vert_24${IconExtension}")

    val NavigateBefore: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_navigate_before_24${IconExtension}")

    val RadioButtonUnchecked: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_radio_button_unchecked_24${IconExtension}")

    val Replay: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_replay_24${IconExtension}")

    val Schedule: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_schedule_24${IconExtension}")

    val Settings: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_settings_24${IconExtension}")

    val TaskAlt: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_task_alt_24${IconExtension}")

    val Warning: Painter
        @Composable
        get() = painterResource("${IconsPath}ic_rounded_warning_24${IconExtension}")
}
