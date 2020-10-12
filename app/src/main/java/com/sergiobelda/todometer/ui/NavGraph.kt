/*
 * Copyright 2020 Sergio Belda
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

package com.sergiobelda.todometer.ui

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.sergiobelda.todometer.ui.utils.Navigator
import kotlinx.android.parcel.Parcelize

sealed class Destination : Parcelable {
    @Parcelize
    object Home : Destination()

    @Parcelize
    object AddTask : Destination()

    @Immutable
    @Parcelize
    data class TaskDetail(val taskId: Long) : Destination()
}

class Actions(navigator: Navigator<Destination>) {
    val selectTask: (Long) -> Unit = { taskId: Long ->
        navigator.navigate(Destination.TaskDetail(taskId))
    }
    val addTask: () -> Unit = {
        navigator.navigate(Destination.AddTask)
    }
    val upPress: () -> Unit = {
        navigator.back()
    }
}
