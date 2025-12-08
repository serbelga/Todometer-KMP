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

package dev.sergiobelda.todometer.common.data.localdatasource

import dev.sergiobelda.todometer.common.database.dao.ITaskListDao
import dev.sergiobelda.todometer.common.database.mapper.asTaskList
import dev.sergiobelda.todometer.common.database.mapper.asTaskListEntity
import dev.sergiobelda.todometer.common.database.testutils.taskList1
import dev.sergiobelda.todometer.common.database.testutils.taskListEntities
import dev.sergiobelda.todometer.common.database.testutils.taskListEntity1
import dev.sergiobelda.todometer.common.domain.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TaskListLocalDataSourceTest {
    @MockK
    private val taskListDao: ITaskListDao = mockk(relaxed = true)

    private val taskListLocalDataSource = TaskListLocalDataSource(taskListDao)

    @Test
    fun testGetTaskList() =
        runTest {
            coEvery { taskListDao.getTaskList("1") } returns
                flow {
                    emit(taskListEntity1)
                }

            val result = taskListLocalDataSource.getTaskList("1").first()
            assertTrue { result is Result.Success }
            assertEquals(taskListEntity1.asTaskList(), (result as? Result.Success)?.value)
        }

    @Test
    fun testGetTaskListNotExist() =
        runTest {
            coEvery { taskListDao.getTaskList("1") } returns
                flow {
                    emit(null)
                }

            val result = taskListLocalDataSource.getTaskList("1").first()
            assertTrue { result is Result.Error }
        }

    @Test
    fun testGetTaskLists() =
        runTest {
            coEvery { taskListDao.getTaskLists() } returns
                flow {
                    emit(taskListEntities)
                }

            val result = taskListLocalDataSource.getTaskLists().first()
            assertTrue { result is Result.Success }
            assertEquals(
                taskListEntities.map { it.asTaskList() },
                (result as? Result.Success)?.value,
            )
        }

    @Test
    fun testInsertTaskList() =
        runTest {
            coEvery { taskListDao.insertTaskList(taskListEntity1) } returns taskListEntity1.id

            val result = taskListLocalDataSource.insertTaskList(taskListEntity1.asTaskList())
            assertTrue { result is Result.Success }
        }

    @Test
    fun testUpdateTaskList() =
        runTest {
            taskListLocalDataSource.updateTaskList(taskList1)

            coVerify { taskListDao.updateTaskList(taskList1.asTaskListEntity()) }
        }

    @Test
    fun testDeleteTaskList() =
        runTest {
            taskListLocalDataSource.deleteTaskList("1")

            coVerify { taskListDao.deleteTaskList("1") }
        }
}
