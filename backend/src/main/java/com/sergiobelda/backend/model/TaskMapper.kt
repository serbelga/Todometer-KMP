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

package com.sergiobelda.backend.model

import com.sergiobelda.backend.database.entity.NewTaskEntity
import com.sergiobelda.backend.database.entity.TaskEntity
import java.util.UUID

fun TaskEntity.toTask() = Task(
    id = id.toString(),
    title = title,
    description = description,
    state = state,
    projectId = projectId.toString(),
    tag = tag
)

fun Iterable<TaskEntity>.toTaskList() = this.map { taskEntity ->
    taskEntity.toTask()
}

fun NewTask.toNewTaskEntity() = NewTaskEntity(
    id = id?.let { UUID.fromString(it) },
    title = title,
    description = description,
    state = state,
    projectId = UUID.fromString(projectId),
    tag = tag
)
