/*
 * Copyright 2020 Sergio Belda
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

package com.sergiobelda.todometer.mapper

import com.sergiobelda.todometer.db.view.TaskProjectView
import com.sergiobelda.todometer.model.TaskProject

@Deprecated("Moved to common module")
object TaskProjectMapper {

    fun TaskProjectView.toDomain() = TaskProject(
        id = task.id,
        title = task.title,
        description = task.description,
        state = task.state,
        tagId = task.tagId,
        projectId = task.projectId,
        projectName = projectName
    )
}
