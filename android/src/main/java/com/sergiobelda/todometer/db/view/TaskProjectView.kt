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

package com.sergiobelda.todometer.db.view

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Embedded
import com.sergiobelda.todometer.db.entity.TaskEntity

/**
 *
 */
@DatabaseView(
    "SELECT " +
        " t.*," +
        " p.name as project_name" +
        " FROM Task t LEFT JOIN Project p ON t.project_id = p.id" +
        " ORDER BY project_id"
)
@Deprecated("Moved to common module")
data class TaskProjectView(
    @Embedded val task: TaskEntity,
    @ColumnInfo(name = "project_name") val projectName: String?
)
