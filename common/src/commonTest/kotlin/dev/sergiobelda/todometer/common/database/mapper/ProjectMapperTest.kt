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

import dev.sergiobelda.todometer.ProjectEntity
import dev.sergiobelda.todometer.common.model.Project
import kotlin.test.Test
import kotlin.test.assertEquals

class ProjectMapperTest {

    @Test
    fun testProjectToProjectEntity() {
        val project = Project(
            id = "1",
            name = "Name",
            description = "Description",
            sync = true
        )
        val projectEntity = project.toEntity()
        assertEquals(project.id, projectEntity.id)
        assertEquals(project.name, projectEntity.name)
        assertEquals(project.description, projectEntity.description)
        assertEquals(project.sync, projectEntity.sync)
    }

    @Test
    fun testProjectEntityToProject() {
        val projectEntity = ProjectEntity(
            id = "1",
            name = "Name",
            description = "Description",
            sync = true
        )
        val project = projectEntity.toDomain()
        assertEquals(projectEntity.id, project.id)
        assertEquals(projectEntity.name, project.name)
        assertEquals(projectEntity.description, project.description)
        assertEquals(projectEntity.sync, project.sync)
    }
}
