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
import dev.sergiobelda.todometer.common.testutils.entityProjects
import dev.sergiobelda.todometer.common.testutils.projectEntity1
import dev.sergiobelda.todometer.common.testutils.projectEntity1Updated
import dev.sergiobelda.todometer.common.testutils.projectEntity2
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
class ProjectDaoTest : DatabaseTest() {

    private lateinit var projectDao: ProjectDao

    @BeforeTest
    fun init() {
        projectDao = ProjectDao(database)
    }

    @Test
    fun testGetProjects() = runTest {
        projectDao.insertProject(projectEntity1)
        projectDao.insertProject(projectEntity2)
        val list = projectDao.getProjects().first()
        assertTrue { list.size == 2 }
    }

    @Test
    fun testGetProject() = runTest {
        val id = projectDao.insertProject(projectEntity1)
        assertNotNull(projectDao.getProject(id).first())
    }

    @Test
    fun testGetProjectNotExist() = runTest {
        assertNull(projectDao.getProject("1").first())
    }

    @Test
    fun testInsertProject() = runTest {
        val id = projectDao.insertProject(projectEntity1)
        assertEquals(projectEntity1, projectDao.getProject(id).first())
    }

    @Test
    fun testInsertProjects() = runTest {
        projectDao.insertProjects(entityProjects)
        val list = projectDao.getProjects().first()
        assertTrue { list.containsAll(entityProjects) }
    }

    @Test
    fun testUpdateProject() = runTest {
        val id = projectDao.insertProject(projectEntity1)
        var project = projectDao.getProject(id).first()
        assertEquals("Project 1", project?.name)

        projectDao.updateProject(projectEntity1Updated)

        project = projectDao.getProject(id).first()
        assertEquals("Project 1 Updated", project?.name)
    }

    @Test
    fun testDeleteProject() = runTest {
        val id = projectDao.insertProject(projectEntity1)
        assertNotNull(projectDao.getProject(id).first())
        projectDao.deleteProject(id)
        assertNull(projectDao.getProject(id).first())
    }
}
