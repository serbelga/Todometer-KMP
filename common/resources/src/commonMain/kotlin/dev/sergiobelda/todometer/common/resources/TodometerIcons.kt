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

object TodometerIcons {
    val Add: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_add_24)

    val ArrowBack: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_arrow_back_24)

    val Check: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_check_24)

    val CheckBox: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_check_box_24)

    val CheckCircle: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_check_circle_24)

    val Close: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_close_24)

    val Code: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_code_24)

    val Contrast: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_contrast_24)

    val DarkMode: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_dark_mode_24)

    val Delete: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_delete_24)

    val Description: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_description_24)

    val Edit: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_edit_24)

    val Event: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_event_24)

    val ExpandLess: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_expand_less_24)

    val ExpandMore: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_expand_more_24)

    val GitHub: Painter
        @Composable
        get() = painterResource(MR.images.ic_github_24)

    val Info: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_info_24)

    val LightMode: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_light_mode_24)

    val Menu: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_menu_24)

    val MoreVert: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_more_vert_24)

    val NavigateBefore: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_navigate_before_24)

    val RadioButtonUnchecked: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_radio_button_unchecked_24)

    val Replay: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_replay_24)

    val Schedule: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_schedule_24)

    val Settings: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_settings_24)

    val TaskAlt: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_task_alt_24)

    val Warning: Painter
        @Composable
        get() = painterResource(MR.images.ic_rounded_warning_24)
}
