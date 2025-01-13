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

import dev.sergiobelda.todometer.common.ui.extensions.format
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class TaskDueDateTest {

    @Test
    fun testGetDueDateFormatted() {
        val localDateTime = LocalDateTime(2020, 3, 1, 16, 25)
        val instant = localDateTime.toInstant(TimeZone.UTC)
        assertEquals(
            "01-03-2020 16:25h",
            TaskDueDate.getDueDateFormatted(instant.toEpochMilliseconds()),
        )
    }

    @Test
    fun testGetDueDateFormattedToday() {
        val instant = Clock.System.now()
        val localDateTime = instant.toLocalDateTime(TimeZone.UTC)
        assertEquals(
            "${localDateTime.hour.format(2)}:${localDateTime.minute.format(2)}h",
            TaskDueDate.getDueDateFormatted(instant.toEpochMilliseconds()),
        )
    }
}
