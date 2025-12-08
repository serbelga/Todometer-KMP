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

package dev.sergiobelda.todometer.wearapp.wearos.ui.tooling.util

import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.domain.model.TaskList
import dev.sergiobelda.todometer.common.domain.model.TaskState

internal val taskSample =
    Task(
        id = "0",
        title = "The task title",
        description = "",
        tag = Tag.UNSPECIFIED,
        dueDate = 0,
        state = TaskState.DOING,
        taskListId = "0",
        isPinned = false,
        sync = false,
    )

internal val taskItemSample =
    TaskItem(
        id = "0",
        title = "The task title",
        tag = Tag.UNSPECIFIED,
        dueDate = 0,
        state = TaskState.DOING,
        taskListId = "0",
        isPinned = false,
        sync = false,
        checklistItemsDone = 0,
        totalChecklistItems = 0,
    )

internal val taskListSample =
    TaskList(
        id = "0",
        name = "Custom task list",
        description = "",
        sync = true,
    )
