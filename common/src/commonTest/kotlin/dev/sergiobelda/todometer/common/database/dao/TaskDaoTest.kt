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

package dev.sergiobelda.todometer.common.database.dao

import dev.sergiobelda.todometer.common.database.DatabaseTest
import dev.sergiobelda.todometer.common.model.Tag
import dev.sergiobelda.todometer.common.model.TaskState
import dev.sergiobelda.todometer.common.testutil.TestUtil
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
class TaskDaoTest : DatabaseTest() {

    private lateinit var taskDao: TaskDao

    private lateinit var projectDao: ProjectDao

    @BeforeTest
    fun init() {
        taskDao = TaskDao(database)
        projectDao = ProjectDao(database)
        runTest {
            projectDao.insertProject(TestUtil.createProjectEntity())
        }
    }

    @Test
    fun testInsertTask() = runTest {
        val taskA = TestUtil.createTaskEntity()
        val id = taskDao.insertTask(taskA)
        val taskB = taskDao.getTask(id).first()
        assertEquals(taskB, taskA)
    }

    @Test
    fun testGetTask() = runTest {
        val id = taskDao.insertTask(TestUtil.createTaskEntity())
        assertNotNull(taskDao.getTask(id).first())
    }

    @Test
    fun testGetTaskNotExist() = runTest {
        assertNull(taskDao.getTask("1").first())
    }

    @Test
    fun testGetTasks() = runTest {
        val task = TestUtil.createTaskEntity()
        taskDao.insertTask(task)
        val list = taskDao.getTasks("1").first()
        assertTrue { list.contains(task) }
    }

    @Test
    fun testUpdateTask() = runTest {
        val id = taskDao.insertTask(TestUtil.createTaskEntity())
        var task = taskDao.getTask(id).first()
        assertEquals("Title", task?.title)
        assertEquals("Description", task?.description)
        assertEquals(Tag.GRAY.name, task?.tag)

        taskDao.updateTask(TestUtil.createUpdatedTaskEntity())

        task = taskDao.getTask(id).first()
        assertEquals("New title", task?.title)
        assertEquals("New description", task?.description)
        assertEquals(Tag.RED.name, task?.tag)
    }

    @Test
    fun testUpdateState() = runTest {
        val id = taskDao.insertTask(TestUtil.createTaskEntity())
        var task = taskDao.getTask(id).first()
        assertEquals(TaskState.DOING.name, task?.state)

        taskDao.updateTaskState(id, TaskState.DONE)
        task = taskDao.getTask(id).first()
        assertEquals(TaskState.DONE.name, task?.state)
    }

    @Test
    fun testDeleteTask() = runTest {
        val id = taskDao.insertTask(TestUtil.createTaskEntity())
        taskDao.getTask(id).first()
        taskDao.deleteTask(id)
        assertNull(taskDao.getTask(id).first())
    }
}
