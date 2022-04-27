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

package dev.sergiobelda.todometer.common.data.database.dao

import dev.sergiobelda.todometer.common.data.database.DatabaseTest
import dev.sergiobelda.todometer.common.testutils.taskListEntities
import dev.sergiobelda.todometer.common.testutils.taskListEntity1
import dev.sergiobelda.todometer.common.testutils.taskListEntity1Updated
import dev.sergiobelda.todometer.common.testutils.taskListEntity2
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class TaskListDaoTest : DatabaseTest() {

    private lateinit var taskListDao: TaskListDao

    @BeforeTest
    fun init() {
        taskListDao = TaskListDao(database)
    }

    @Test
    fun testGetTaskLists() = runTest {
        taskListDao.insertTaskList(taskListEntity1)
        taskListDao.insertTaskList(taskListEntity2)
        val list = taskListDao.getTaskLists().first()
        assertTrue { list.size == 2 }
    }

    @Test
    fun testGetTaskList() = runTest {
        val id = taskListDao.insertTaskList(taskListEntity1)
        assertNotNull(taskListDao.getTaskList(id).first())
    }

    @Test
    fun testGetTaskListNotExist() = runTest {
        assertNull(taskListDao.getTaskList("1").first())
    }

    @Test
    fun testInsertTaskList() = runTest {
        val id = taskListDao.insertTaskList(taskListEntity1)
        assertEquals(taskListEntity1, taskListDao.getTaskList(id).first())
    }

    @Test
    fun testInsertTaskLists() = runTest {
        taskListDao.insertTaskLists(taskListEntities)
        val list = taskListDao.getTaskLists().first()
        assertTrue { list.containsAll(taskListEntities) }
    }

    @Test
    fun testUpdateTaskList() = runTest {
        val id = taskListDao.insertTaskList(taskListEntity1)
        var taskList = taskListDao.getTaskList(id).first()
        assertEquals("Task List 1", taskList?.name)

        taskListDao.updateTaskList(taskListEntity1Updated)

        taskList = taskListDao.getTaskList(id).first()
        assertEquals("Task List 1 Updated", taskList?.name)
    }

    @Test
    fun testUpdateTaskListName() = runTest {
        val id = taskListDao.insertTaskList(taskListEntity1)
        var taskList = taskListDao.getTaskList(id).first()
        assertEquals("Task List 1", taskList?.name)

        val updatedName = "updated name"
        taskListDao.updateTaskListName(id, updatedName)

        taskList = taskListDao.getTaskList(id).first()
        assertEquals(updatedName, taskList?.name)
    }

    @Test
    fun testDeleteTaskList() = runTest {
        val id = taskListDao.insertTaskList(taskListEntity1)
        assertNotNull(taskListDao.getTaskList(id).first())
        taskListDao.deleteTaskList(id)
        assertNull(taskListDao.getTaskList(id).first())
    }
}
