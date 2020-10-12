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

package com.sergiobelda.todometer.sampledata

import com.sergiobelda.todometer.model.Task
import com.sergiobelda.todometer.model.TaskState

val task1 = Task(
    title = "Task 1",
    body = "Task one",
    taskState = TaskState.DOING
)

val task2 = Task(
    title = "Task 2",
    body = "Task two",
    taskState = TaskState.DONE
)

val tasksList = listOf(task1, task2)
