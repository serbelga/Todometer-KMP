/*
 * Copyright 2024 Sergio Belda
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

package dev.sergiobelda.todometer.common.ui.extensions

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Return date in format: HH:mm.
 */
fun Long.timeFormat(): String {
    val localDateTime: LocalDateTime = this.toLocalDateTime()

    return "${localDateTime.hour.format(2)}:${localDateTime.minute.format(2)}h"
}

/**
 * Return date in format: dd-MM-yyyy.
 */
fun Long.dateFormat(): String {
    val localDateTime: LocalDateTime = this.toLocalDateTime()

    return "${localDateTime.dayOfMonth.format(2)}-${localDateTime.monthNumber.format(2)}-${localDateTime.year}"
}

fun Long.localTime(): LocalTime = this.toLocalDateTime().time

@OptIn(ExperimentalTime::class)
internal fun Long.toLocalDateTime(): LocalDateTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.UTC)
