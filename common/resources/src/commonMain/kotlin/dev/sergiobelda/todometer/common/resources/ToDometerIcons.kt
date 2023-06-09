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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
object ToDometerIcons {
    val Add: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_add_24.xml")

    val ArrowBack: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_arrow_back_24.xml")

    val Check: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_check_24.xml")

    val CheckBox: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_check_box_24.xml")

    val CheckCircle: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_check_circle_24.xml")

    val Close: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_close_24.xml")

    val Code: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_code_24.xml")

    val Contrast: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_contrast_24.xml")

    val DarkMode: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_dark_mode_24.xml")

    val Delete: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_delete_24.xml")

    val Description: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_description_24.xml")

    val Edit: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_edit_24.xml")

    val Event: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_event_24.xml")

    val ExpandLess: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_expand_less_24.xml")

    val ExpandMore: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_expand_more_24.xml")

    val GitHub: Painter
        @Composable
        get() = painterResource("icons/ic_github_24.xml")

    val Info: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_info_24.xml")

    val LightMode: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_light_mode_24.xml")

    val Menu: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_menu_24.xml")

    val MoreVert: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_more_vert_24.xml")

    val NavigateBefore: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_navigate_before_24.xml")

    val RadioButtonUnchecked: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_radio_button_unchecked_24.xml")

    val Replay: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_replay_24.xml")

    val Settings: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_settings_24.xml")

    val TaskAlt: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_task_alt_24.xml")

    val Warning: Painter
        @Composable
        get() = painterResource("icons/ic_rounded_warning_24.xml")
}
