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

package dev.sergiobelda.todometer.common.ui.task

import dev.sergiobelda.todometer.common.ui.extensions.dateFormat
import dev.sergiobelda.todometer.common.ui.extensions.timeFormat
import dev.sergiobelda.todometer.common.ui.extensions.toLocalDateTime
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

object TaskDueDate {

    /**
     * Returns due date formatted. If due date is today, it only shows HH:mm,
     * otherwise it displays dd-MM-yyyy HH:mm.
     */
    @OptIn(ExperimentalTime::class)
    fun getDueDateFormatted(dueDate: Long): String {
        val localDateTime: LocalDateTime = dueDate.toLocalDateTime()
        val todayLocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

        return if (localDateTime.date.compareTo(todayLocalDate) == 0) {
            dueDate.timeFormat()
        } else {
            "${dueDate.dateFormat()} ${dueDate.timeFormat()}"
        }
    }
}
