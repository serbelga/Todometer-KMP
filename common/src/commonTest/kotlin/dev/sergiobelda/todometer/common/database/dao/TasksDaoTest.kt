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
import dev.sergiobelda.todometer.common.testutil.TestUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class TasksDaoTest : DatabaseTest() {

    lateinit var taskDao: TaskDao

    lateinit var projectDao: ProjectDao

    @BeforeTest
    fun init() {
        taskDao = TaskDao(database)
        projectDao = ProjectDao(database)
        runTest {
            projectDao.insertProject(TestUtil.createProjectEntity())
        }
    }

    @Test
    fun testGetTask() = runTest {
        val taskA = TestUtil.createTaskEntity()
        taskDao.insertTask(taskA)
        val taskB = taskDao.getTask("1").firstOrNull()
        assertEquals(taskB, taskA)
    }
}