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

package com.sergiobelda.backend.database.mapper

import com.sergiobelda.backend.database.entity.TaskEntity
import com.sergiobelda.backend.database.entity.TaskTagView
import com.sergiobelda.backend.database.table.TagTable
import com.sergiobelda.backend.database.table.TaskTable
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toTaskEntity() =
    TaskEntity(
        id = this[TaskTable.id],
        title = this[TaskTable.title],
        description = this[TaskTable.description],
        state = this[TaskTable.state],
        projectId = this[TaskTable.projectId],
        tagId = this[TaskTable.tagId]
    )

fun Iterable<ResultRow>.toTaskEntityList() = this.map {
    it.toTaskEntity()
}

fun ResultRow.toTaskTagView() =
    TaskTagView(
        id = this[TaskTable.id],
        title = this[TaskTable.title],
        description = this[TaskTable.description],
        state = this[TaskTable.state],
        projectId = this[TaskTable.projectId],
        tagId = this[TaskTable.tagId],
        tagName = this[TagTable.name],
        tagColor = this[TagTable.color]
    )

fun Iterable<ResultRow>.toTaskTagViewList() = this.map {
    it.toTaskTagView()
}