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

package dev.sergiobelda.todometer.common.data.database.mapper

import dev.sergiobelda.todometer.common.data.database.TaskEntity
import dev.sergiobelda.todometer.common.domain.model.Task

fun TaskEntity.toDomain() = Task(
    id = id,
    title = title,
    tag = tag,
    description = description,
    dueDate = dueDate,
    state = state,
    taskListId = tasklist_id,
    sync = sync
)

fun Iterable<TaskEntity>.toDomain() = this.map {
    it.toDomain()
}

fun Task.toEntity() = TaskEntity(
    id = id,
    title = title,
    tag = tag,
    description = description,
    dueDate = dueDate,
    state = state,
    tasklist_id = taskListId,
    sync = sync
)
