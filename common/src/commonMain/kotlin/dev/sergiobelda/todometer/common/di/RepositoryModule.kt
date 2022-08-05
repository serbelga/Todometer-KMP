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

package dev.sergiobelda.todometer.common.di

import dev.sergiobelda.todometer.common.data.repository.TaskChecklistItemsRepository
import dev.sergiobelda.todometer.common.data.repository.TaskListRepository
import dev.sergiobelda.todometer.common.data.repository.TaskRepository
import dev.sergiobelda.todometer.common.data.repository.UserPreferencesRepository
import dev.sergiobelda.todometer.common.domain.repository.ITaskChecklistItemsRepository
import dev.sergiobelda.todometer.common.domain.repository.ITaskListRepository
import dev.sergiobelda.todometer.common.domain.repository.ITaskRepository
import dev.sergiobelda.todometer.common.domain.repository.IUserPreferencesRepository
import org.koin.dsl.module

internal val repositoryModule = module {
    single<ITaskListRepository> {
        TaskListRepository(get(), get())
    }
    single<ITaskRepository> {
        TaskRepository(get(), get())
    }
    single<IUserPreferencesRepository> {
        UserPreferencesRepository(get())
    }
    single<ITaskChecklistItemsRepository> {
        TaskChecklistItemsRepository(get())
    }
}
