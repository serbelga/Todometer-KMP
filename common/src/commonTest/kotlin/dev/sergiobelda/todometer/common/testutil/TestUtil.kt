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

package dev.sergiobelda.todometer.common.testutil

import dev.sergiobelda.todometer.ProjectEntity
import dev.sergiobelda.todometer.TaskEntity

object TestUtil {

    fun createProjectEntity(): ProjectEntity = ProjectEntity(
        id = "1",
        name = "Name",
        description = "Description",
        sync = false
    )

    fun createTaskEntity(): TaskEntity = TaskEntity(
        id = "1",
        title = "Title",
        description = "Description",
        state = "DOING",
        project_id = "1",
        tag = "GRAY",
        sync = false
    )
}
