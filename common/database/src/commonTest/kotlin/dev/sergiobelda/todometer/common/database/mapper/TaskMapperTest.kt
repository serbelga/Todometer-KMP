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

import dev.sergiobelda.todometer.common.database.TaskEntity
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskState
import kotlin.test.Test
import kotlin.test.assertEquals

class TaskMapperTest {
    @Test
    fun testTaskToTaskEntity() {
        val task =
            Task(
                id = "1",
                title = "Title",
                tag = Tag.GRAY,
                description = "Description",
                dueDate = 1649887517234,
                state = TaskState.DOING,
                taskListId = "2",
                isPinned = false,
                sync = true,
            )
        val taskEntity = task.asTaskEntity()
        assertEquals(task.id, taskEntity.id)
        assertEquals(task.title, taskEntity.title)
        assertEquals(task.description, taskEntity.description)
        assertEquals(task.state, taskEntity.state)
        assertEquals(task.taskListId, taskEntity.tasklist_id)
        assertEquals(task.tag, taskEntity.tag)
        assertEquals(task.isPinned, taskEntity.isPinned)
        assertEquals(task.sync, taskEntity.sync)
    }

    @Test
    fun testTaskEntityToTask() {
        val taskEntity =
            TaskEntity(
                id = "1",
                title = "Title",
                tag = Tag.GRAY,
                description = "Description",
                dueDate = 1649887517234,
                state = TaskState.DOING,
                tasklist_id = "2",
                isPinned = false,
                sync = true,
            )
        val task = taskEntity.asTask()
        assertEquals(taskEntity.id, task.id)
        assertEquals(taskEntity.title, task.title)
        assertEquals(taskEntity.description, task.description)
        assertEquals(taskEntity.state, task.state)
        assertEquals(taskEntity.tasklist_id, task.taskListId)
        assertEquals(taskEntity.tag, task.tag)
        assertEquals(taskEntity.isPinned, task.isPinned)
        assertEquals(taskEntity.sync, task.sync)
    }
}
