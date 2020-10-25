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
import com.sergiobelda.todometer.model.TaskState

@DatabaseView(
    "SELECT p.id as project_id, p.name as project_name, p.description as project_description," +
        " t.id as task_id, t.title as task_title, t.description as task_description, t.state as task_state, t.tag_id as task_tag_id" +
        " FROM Project p INNER JOIN Task t ON p.id = t.project_id"
)
data class ProjectTaskView(
    @ColumnInfo(name = "project_id") val projectId: Int,
    @ColumnInfo(name = "project_name") val projectName: String,
    @ColumnInfo(name = "project_description") val projectDescription: String,
    @ColumnInfo(name = "task_id") val taskId: Int,
    @ColumnInfo(name = "task_title") val taskTitle: String,
    @ColumnInfo(name = "task_description") val taskDescription: String,
    @ColumnInfo(name = "task_state") val state: TaskState,
    @ColumnInfo(name = "task_tag_id") val taskTagId: Int
)
