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
    fun testInsertProject() = runTest {
        val projectA = TestUtil.createProjectEntity()
        val id = projectDao.insertProject(projectA)
        val projectB = projectDao.getProject(id).first()
        assertEquals(projectB, projectA)
    }

    @Test
    fun testGetProject() = runTest {
        val id = projectDao.insertProject(TestUtil.createProjectEntity())
        assertNotNull(projectDao.getProject(id).first())
    }

    @Test
    fun testGetProjectNotExist() = runTest {
        assertNull(projectDao.getProject("1").first())
    }

    @Test
    fun testGetProjects() = runTest {
        val project = TestUtil.createProjectEntity()
        projectDao.insertProject(project)
        val list = projectDao.getProjects().first()
        assertTrue { list.contains(project) }
    }
}
