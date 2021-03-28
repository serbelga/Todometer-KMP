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

package com.sergiobelda.todometer.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.sergiobelda.todometer.db.entity.ProjectEntity
import com.sergiobelda.todometer.db.entity.ProjectTasksEntity
import kotlinx.coroutines.flow.Flow

@Dao
@Deprecated("Use DAO in common module")
interface ProjectDao {

    @Query("SELECT * FROM project WHERE id = :id")
    fun getProject(id: Int): Flow<ProjectTasksEntity>

    @Query("SELECT * FROM project ORDER BY id ASC")
    fun getProjects(): Flow<List<ProjectEntity>>

    @Transaction
    @Query("SELECT * FROM project")
    fun getProjectTasksList(): Flow<List<ProjectTasksEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProject(project: ProjectEntity)

    @Update
    suspend fun updateProject(project: ProjectEntity)

    @Query("DELETE FROM project WHERE id = :id")
    suspend fun deleteProject(id: Int)
}
