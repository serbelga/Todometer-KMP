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

import dev.sergiobelda.todometer.TaskEntity
import dev.sergiobelda.todometer.common.model.Tag
import dev.sergiobelda.todometer.common.model.Task
import dev.sergiobelda.todometer.common.model.TaskState
import kotlin.test.Test
import kotlin.test.assertEquals

class TaskMapperTest {

    @Test
    fun testTaskToTaskEntity() {
        val task = Task(
            id = "1",
            title = "Title",
            description = "Description",
            state = TaskState.DOING,
            projectId = "2",
            tag = Tag.GRAY,
            sync = true
        )
        val taskEntity = task.toEntity()
        assertEquals(task.id, taskEntity.id)
        assertEquals(task.title, taskEntity.title)
        assertEquals(task.description, taskEntity.description)
        assertEquals(task.state, taskEntity.state)
        assertEquals(task.projectId, taskEntity.project_id)
        assertEquals(task.tag, taskEntity.tag)
        assertEquals(task.sync, taskEntity.sync)
    }

    @Test
    fun testTaskEntityToTask() {
        val taskEntity = TaskEntity(
            id = "1",
            title = "Title",
            description = "Description",
            state = TaskState.DOING,
            project_id = "2",
            tag = Tag.GRAY,
            sync = true
        )
        val task = taskEntity.toDomain()
        assertEquals(taskEntity.id, task.id)
        assertEquals(taskEntity.title, task.title)
        assertEquals(taskEntity.description, task.description)
        assertEquals(taskEntity.state, task.state)
        assertEquals(taskEntity.project_id, task.projectId)
        assertEquals(taskEntity.tag, task.tag)
        assertEquals(taskEntity.sync, task.sync)
    }
}
