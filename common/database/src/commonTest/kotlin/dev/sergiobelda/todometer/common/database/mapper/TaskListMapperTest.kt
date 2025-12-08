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

package dev.sergiobelda.todometer.common.database.mapper

import dev.sergiobelda.todometer.common.database.TaskListEntity
import dev.sergiobelda.todometer.common.domain.model.TaskList
import kotlin.test.Test
import kotlin.test.assertEquals

class TaskListMapperTest {
    @Test
    fun testTaskListToTaskListEntity() {
        val taskList =
            TaskList(
                id = "1",
                name = "Name",
                description = "Description",
                sync = true,
            )
        val taskListEntity = taskList.asTaskListEntity()
        assertEquals(taskList.id, taskListEntity.id)
        assertEquals(taskList.name, taskListEntity.name)
        assertEquals(taskList.description, taskListEntity.description)
        assertEquals(taskList.sync, taskListEntity.sync)
    }

    @Test
    fun testTaskListEntityToTaskList() {
        val taskListEntity =
            TaskListEntity(
                id = "1",
                name = "Name",
                description = "Description",
                sync = true,
            )
        val taskList = taskListEntity.asTaskList()
        assertEquals(taskListEntity.id, taskList.id)
        assertEquals(taskListEntity.name, taskList.name)
        assertEquals(taskListEntity.description, taskList.description)
        assertEquals(taskListEntity.sync, taskList.sync)
    }
}
