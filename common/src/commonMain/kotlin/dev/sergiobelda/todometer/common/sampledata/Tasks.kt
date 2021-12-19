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

package dev.sergiobelda.todometer.common.sampledata

import dev.sergiobelda.todometer.common.model.Tag
import dev.sergiobelda.todometer.common.model.Task
import dev.sergiobelda.todometer.common.model.TaskState

val sampleTasks = listOf(
    Task(
        id = "1",
        title = "Task 1",
        description = "This is the Task 1",
        state = TaskState.DOING,
        taskListId = "1",
        tag = Tag.BLUE,
        sync = false
    ),
    Task(
        id = "2",
        title = "Task 2",
        description = "This is the Task 2",
        state = TaskState.DONE,
        taskListId = "1",
        tag = Tag.BLUE,
        sync = false
    )
)
