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

import com.sergiobelda.todometer.repository.ProjectRepository
import com.sergiobelda.todometer.repository.TaskRepository
import com.sergiobelda.todometer.usecase.DeleteTaskUseCase
import com.sergiobelda.todometer.usecase.GetProjectListUseCase
import com.sergiobelda.todometer.usecase.GetTaskUseCase
import com.sergiobelda.todometer.usecase.InsertProjectUseCase
import com.sergiobelda.todometer.usecase.InsertTaskUseCase
import com.sergiobelda.todometer.usecase.UpdateTaskStateUseCase
import com.sergiobelda.todometer.usecase.UpdateTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetTaskUseCase(taskRepository: TaskRepository) = GetTaskUseCase(taskRepository)

    @Provides
    fun provideUpdateTaskStateUseCase(taskRepository: TaskRepository) = UpdateTaskStateUseCase(taskRepository)

    @Provides
    fun provideUpdateTaskUseCase(taskRepository: TaskRepository) = UpdateTaskUseCase(taskRepository)

    @Provides
    fun provideInsertTaskUseCase(taskRepository: TaskRepository) = InsertTaskUseCase(taskRepository)

    @Provides
    fun provideDeleteTaskUseCase(taskRepository: TaskRepository) = DeleteTaskUseCase(taskRepository)

    @Provides
    fun provideInsertProjectUseCase(projectRepository: ProjectRepository) = InsertProjectUseCase(projectRepository)

    @Provides
    fun provideGetProjectTasksListUseCase(projectRepository: ProjectRepository) = GetProjectListUseCase(projectRepository)
}
