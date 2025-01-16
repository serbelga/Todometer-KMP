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

package dev.sergiobelda.todometer.common.database.testutils

import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskList
import dev.sergiobelda.todometer.common.domain.model.TaskState

val taskList1 = TaskList(
    id = "1",
    name = "Task List 1",
    description = "Description",
    sync = false,
)

val taskList1Updated = TaskList(
    id = "1",
    name = "Task List 1 Updated",
    description = "Description",
    sync = false,
)

val taskList2 = TaskList(
    id = "2",
    name = "Task List 2",
    description = "Description",
    sync = false,
)

val taskLists = listOf(taskList1, taskList2)

val task1 = Task(
    id = "1",
    title = "Task 1",
    tag = Tag.GRAY,
    description = "Description 1",
    dueDate = 1649887517234,
    state = TaskState.DOING,
    taskListId = "1",
    isPinned = false,
    sync = false,
)

val task1Updated = Task(
    id = "1",
    title = "Task 1 Updated",
    tag = Tag.RED,
    description = "Description 1 Updated",
    dueDate = 1649887517234,
    state = TaskState.DOING,
    taskListId = "1",
    isPinned = false,
    sync = false,
)

val task2 = Task(
    id = "2",
    title = "Task 2",
    tag = Tag.RED,
    description = "Description 2",
    dueDate = 1649887517234,
    state = TaskState.DOING,
    taskListId = "1",
    isPinned = false,
    sync = false,
)

val tasks = listOf(task1, task2)
