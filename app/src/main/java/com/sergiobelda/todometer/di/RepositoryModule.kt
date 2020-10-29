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

package com.sergiobelda.todometer.di

import com.sergiobelda.todometer.db.dao.ProjectDao
import com.sergiobelda.todometer.db.dao.TaskDao
import com.sergiobelda.todometer.db.dao.TaskProjectDao
import com.sergiobelda.todometer.repository.ProjectRepository
import com.sergiobelda.todometer.repository.TaskProjectRepository
import com.sergiobelda.todometer.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideTaskRepository(taskDao: TaskDao) = TaskRepository(taskDao)

    @Provides
    @ActivityRetainedScoped
    fun provideProjectRepository(projectDao: ProjectDao) = ProjectRepository(projectDao)

    @Provides
    @ActivityRetainedScoped
    fun provideTaskProjectRepository(taskProjectDao: TaskProjectDao) = TaskProjectRepository(taskProjectDao)
}
