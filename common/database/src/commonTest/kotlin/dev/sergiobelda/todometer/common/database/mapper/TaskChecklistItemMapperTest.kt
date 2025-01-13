/*
 * Copyright 2022 Sergio Belda
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

import dev.sergiobelda.todometer.common.database.TaskChecklistItemEntity
import dev.sergiobelda.todometer.common.domain.model.TaskChecklistItem
import dev.sergiobelda.todometer.common.domain.model.TaskChecklistItemState
import kotlin.test.Test
import kotlin.test.assertEquals

class TaskChecklistItemMapperTest {

    @Test
    fun testTaskChecklistItemToTaskChecklistItemEntity() {
        val taskChecklistItem = TaskChecklistItem(
            id = "1",
            text = "Text",
            state = TaskChecklistItemState.CHECKED,
            taskId = "2",
        )
        val taskChecklistItemEntity = taskChecklistItem.asTaskChecklistItemEntity()
        assertEquals(taskChecklistItem.id, taskChecklistItemEntity.id)
        assertEquals(taskChecklistItem.text, taskChecklistItemEntity.text)
        assertEquals(taskChecklistItem.state, taskChecklistItemEntity.state)
        assertEquals(taskChecklistItem.taskId, taskChecklistItemEntity.task_id)
    }

    @Test
    fun testTaskChecklistItemEntityToTaskChecklistItem() {
        val taskChecklistItemEntity = TaskChecklistItemEntity(
            id = "1",
            text = "Text",
            state = TaskChecklistItemState.CHECKED,
            task_id = "2",
        )
        val taskChecklistItem = taskChecklistItemEntity.asTaskChecklistItem()
        assertEquals(taskChecklistItemEntity.id, taskChecklistItem.id)
        assertEquals(taskChecklistItemEntity.text, taskChecklistItem.text)
        assertEquals(taskChecklistItemEntity.state, taskChecklistItem.state)
        assertEquals(taskChecklistItemEntity.task_id, taskChecklistItem.taskId)
    }
}
