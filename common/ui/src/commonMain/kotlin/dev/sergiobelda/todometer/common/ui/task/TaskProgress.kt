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

package dev.sergiobelda.todometer.common.ui.task

import androidx.annotation.FloatRange
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.domain.model.TaskState

object TaskProgress {

    fun getTasksDoneProgress(list: List<TaskItem?>): Float {
        val size = list.size
        if (size == 0) return 0f
        return list.count { it?.state == TaskState.DONE } / size.toFloat()
    }

    fun getPercentage(
        @FloatRange(from = 0.0, to = 1.0) progress: Float,
    ): String = progress.takeIf {
        it in 0.0..1.0
    }?.let { "${(it * PERCENTAGE_MULTIPLIER).toInt()}%" } ?: "-%"

    private const val PERCENTAGE_MULTIPLIER = 100
}
