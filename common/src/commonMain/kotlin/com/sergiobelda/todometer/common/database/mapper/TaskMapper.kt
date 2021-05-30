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

package com.sergiobelda.todometer.common.database.mapper

import com.sergiobelda.todometer.TaskEntity
import com.sergiobelda.todometer.TaskTagView
import com.sergiobelda.todometer.common.database.DatabaseTypeConverters.colorValueOf
import com.sergiobelda.todometer.common.database.DatabaseTypeConverters.taskStateValueOf
import com.sergiobelda.todometer.common.model.Tag
import com.sergiobelda.todometer.common.model.Task
import com.sergiobelda.todometer.common.model.TaskTag

fun TaskTagView.toDomain() = TaskTag(
    id,
    title,
    description,
    taskStateValueOf(state),
    project_id,
    tag = Tag(
        tag_id,
        colorValueOf(tag_color),
        tag_name
    )
)

fun Iterable<TaskTagView>.toDomain() = this.map {
    it.toDomain()
}

fun Task.toEntity() = TaskEntity(
    id,
    title,
    description,
    state.toString(),
    projectId,
    tagId
)
