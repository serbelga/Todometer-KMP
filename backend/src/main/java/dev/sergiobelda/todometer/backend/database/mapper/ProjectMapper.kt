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

package dev.sergiobelda.todometer.backend.database.mapper

import dev.sergiobelda.todometer.backend.database.entity.TaskListEntity
import dev.sergiobelda.todometer.backend.database.table.TaskListTable
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toTaskListEntity() =
    TaskListEntity(
        id = this[TaskListTable.id],
        name = this[TaskListTable.name],
        description = this[TaskListTable.description]
    )

fun Iterable<ResultRow>.toTaskListEntityList() = this.map {
    it.toTaskListEntity()
}
