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

package com.sergiobelda.todometer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sergiobelda.todometer.db.dao.ProjectDao
import com.sergiobelda.todometer.db.dao.TagDao
import com.sergiobelda.todometer.db.dao.TaskDao
import com.sergiobelda.todometer.db.dao.TaskProjectDao
import com.sergiobelda.todometer.db.entity.ProjectEntity
import com.sergiobelda.todometer.db.entity.TagEntity
import com.sergiobelda.todometer.db.entity.TaskEntity
import com.sergiobelda.todometer.db.view.TaskProjectView

@Database(
    entities = [ProjectEntity::class, TagEntity::class, TaskEntity::class],
    views = [TaskProjectView::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataBaseTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun projectDao(): ProjectDao

    abstract fun tagDao(): TagDao

    abstract fun taskDao(): TaskDao

    abstract fun taskProjectDao(): TaskProjectDao
}
