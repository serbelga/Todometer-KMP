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
import dev.sergiobelda.todometer.common.database.dao.IProjectDao
import dev.sergiobelda.todometer.common.database.mapper.toDomain
import dev.sergiobelda.todometer.common.database.mapper.toEntity
import dev.sergiobelda.todometer.common.testutils.entityProjects
import dev.sergiobelda.todometer.common.testutils.project1
import dev.sergiobelda.todometer.common.testutils.projectEntity1
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ProjectLocalDataSourceTest {

    @MockK
    private val projectDao: IProjectDao = mockk(relaxed = true)

    private val projectLocalDataSource = ProjectLocalDataSource(projectDao)

    @Test
    fun testGetProject() = runTest {
        coEvery { projectDao.getProject("1") } returns flow {
            emit(projectEntity1)
        }

        val result = projectLocalDataSource.getProject("1").first()
        assertTrue { result is Result.Success }
        assertEquals(projectEntity1.toDomain(), (result as? Result.Success)?.value)
    }

    @Test
    fun testGetProjectNotExist() = runTest {
        coEvery { projectDao.getProject("1") } returns flow {
            emit(null)
        }

        val result = projectLocalDataSource.getProject("1").first()
        assertTrue { result is Result.Error }
    }

    @Test
    fun testGetProjects() = runTest {
        coEvery { projectDao.getProjects() } returns flow {
            emit(entityProjects)
        }

        val result = projectLocalDataSource.getProjects().first()
        assertTrue { result is Result.Success }
        assertEquals(
            entityProjects.map { it.toDomain() },
            (result as? Result.Success)?.value
        )
    }

    @Test
    fun testInsertProject() = runTest {
        coEvery { projectDao.insertProject(projectEntity1) } returns projectEntity1.id

        val result = projectLocalDataSource.insertProject(projectEntity1.toDomain())
        assertTrue { result is Result.Success }
    }

    @Test
    fun testUpdateProject() = runTest {
        projectLocalDataSource.updateProject(project1)

        coVerify { projectDao.updateProject(project1.toEntity()) }
    }

    @Test
    fun testDeleteProject() = runTest {
        projectLocalDataSource.deleteProject("1")

        coVerify { projectDao.deleteProject("1") }
    }
}
