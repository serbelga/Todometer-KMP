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

package dev.sergiobelda.todometer.common.testutils

import dev.sergiobelda.todometer.common.model.Project
import dev.sergiobelda.todometer.common.model.Tag
import dev.sergiobelda.todometer.common.model.Task
import dev.sergiobelda.todometer.common.model.TaskState

val project1 = Project(
    id = "1",
    name = "Project 1",
    description = "Description",
    sync = false
)

val project1Updated = Project(
    id = "1",
    name = "Project 1 Updated",
    description = "Description",
    sync = false
)

val project2 = Project(
    id = "2",
    name = "Project 2",
    description = "Description",
    sync = false
)

val projects = listOf(project1, project2)

val task1 = Task(
    id = "1",
    title = "Task 1",
    description = "Description 1",
    state = TaskState.DOING,
    projectId = "1",
    tag = Tag.GRAY,
    sync = false
)

val task1Updated = Task(
    id = "1",
    title = "Task 1 Updated",
    description = "Description 1 Updated",
    state = TaskState.DOING,
    projectId = "1",
    tag = Tag.RED,
    sync = false
)

val task2 = Task(
    id = "2",
    title = "Task 2",
    description = "Description 2",
    state = TaskState.DOING,
    projectId = "1",
    tag = Tag.RED,
    sync = false
)

val tasks = listOf(task1, task2)
