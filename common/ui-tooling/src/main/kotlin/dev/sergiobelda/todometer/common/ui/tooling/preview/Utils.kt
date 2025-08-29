/*
 * Copyright 2025 Sergio Belda
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

package dev.sergiobelda.todometer.common.ui.tooling.preview

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun getTomorrowEpochMilliseconds(): Long {
    val now = Clock.System.now()
    val systemTZ = TimeZone.currentSystemDefault()
    val tomorrow = now.plus(1, DateTimeUnit.DAY, systemTZ)
    return tomorrow.toEpochMilliseconds()
}

@OptIn(ExperimentalTime::class)
fun getYesterdayEpochMilliseconds(): Long {
    val now = Clock.System.now()
    val systemTZ = TimeZone.currentSystemDefault()
    val tomorrow = now.minus(1, DateTimeUnit.DAY, systemTZ)
    return tomorrow.toEpochMilliseconds()
}
