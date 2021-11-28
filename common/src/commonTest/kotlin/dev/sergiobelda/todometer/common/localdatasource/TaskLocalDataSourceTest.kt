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

package dev.sergiobelda.todometer.common.localdatasource

import dev.sergiobelda.todometer.common.data.Result
import dev.sergiobelda.todometer.common.database.dao.TaskDao
import dev.sergiobelda.todometer.common.testutil.TestUtil
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class TaskLocalDataSourceTest {

    @MockK
    private val taskDao: TaskDao = mockk()

    private val taskLocalDataSource = TaskLocalDataSource(taskDao)

    @Test
    fun testGetTask() = runTest {
        val task = TestUtil.createTaskEntity()

        coEvery { taskDao.getTask("1") } returns flow {
            emit(task)
        }

        val taskResult = taskLocalDataSource.getTask("1").first()
        assertTrue { taskResult is Result.Success }
    }

    @Test
    fun testGetTaskNotExist() = runTest {
        coEvery { taskDao.getTask("1") } returns flow {
            emit(null)
        }
        val taskResult = taskLocalDataSource.getTask("1").first()
        assertTrue { taskResult is Result.Error }
    }
}
