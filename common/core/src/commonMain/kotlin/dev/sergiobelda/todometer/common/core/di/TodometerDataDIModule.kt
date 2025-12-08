/*
 * Copyright 2025 Sergio Belda
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

package dev.sergiobelda.todometer.common.core.di

import dev.sergiobelda.todometer.common.data.localdatasource.ITaskChecklistItemLocalDataSource
import dev.sergiobelda.todometer.common.data.localdatasource.ITaskListLocalDataSource
import dev.sergiobelda.todometer.common.data.localdatasource.ITaskLocalDataSource
import dev.sergiobelda.todometer.common.data.localdatasource.TaskChecklistItemLocalDataSource
import dev.sergiobelda.todometer.common.data.localdatasource.TaskListLocalDataSource
import dev.sergiobelda.todometer.common.data.localdatasource.TaskLocalDataSource
import dev.sergiobelda.todometer.common.data.repository.TaskChecklistItemsRepository
import dev.sergiobelda.todometer.common.data.repository.TaskListRepository
import dev.sergiobelda.todometer.common.data.repository.TaskRepository
import dev.sergiobelda.todometer.common.data.repository.UserPreferencesRepository
import dev.sergiobelda.todometer.common.database.createDatabase
import dev.sergiobelda.todometer.common.database.dao.ITaskChecklistItemDao
import dev.sergiobelda.todometer.common.database.dao.ITaskDao
import dev.sergiobelda.todometer.common.database.dao.ITaskListDao
import dev.sergiobelda.todometer.common.database.dao.TaskChecklistItemDao
import dev.sergiobelda.todometer.common.database.dao.TaskDao
import dev.sergiobelda.todometer.common.database.dao.TaskListDao
import dev.sergiobelda.todometer.common.di.TodometerDIModule
import dev.sergiobelda.todometer.common.domain.repository.ITaskChecklistItemsRepository
import dev.sergiobelda.todometer.common.domain.repository.ITaskListRepository
import dev.sergiobelda.todometer.common.domain.repository.ITaskRepository
import dev.sergiobelda.todometer.common.domain.repository.IUserPreferencesRepository
import dev.sergiobelda.todometer.common.preferences.PreferencesFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

data object TodometerDataDIModule : TodometerDIModule {
    override val module: Module =
        module {
            database()
            preferences()
            localDataSource()
            repository()
        }

    private fun Module.database() {
        single { createDatabase() }
        singleOf(::TaskDao) bind ITaskDao::class
        singleOf(::TaskListDao) bind ITaskListDao::class
        singleOf(::TaskChecklistItemDao) bind ITaskChecklistItemDao::class
    }

    private fun Module.preferences() {
        single { PreferencesFactory.createPreferences() }
    }

    private fun Module.localDataSource() {
        singleOf(::TaskListLocalDataSource) bind ITaskListLocalDataSource::class
        singleOf(::TaskLocalDataSource) bind ITaskLocalDataSource::class
        singleOf(::TaskChecklistItemLocalDataSource) bind ITaskChecklistItemLocalDataSource::class
    }

    private fun Module.repository() {
        singleOf(::TaskListRepository) bind ITaskListRepository::class
        singleOf(::TaskRepository) bind ITaskRepository::class
        singleOf(::UserPreferencesRepository) bind IUserPreferencesRepository::class
        singleOf(::TaskChecklistItemsRepository) bind ITaskChecklistItemsRepository::class
    }
}
