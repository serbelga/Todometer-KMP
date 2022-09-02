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

import dev.sergiobelda.todometer.common.data.localdatasource.ITaskChecklistItemLocalDataSource
import dev.sergiobelda.todometer.common.data.localdatasource.ITaskListLocalDataSource
import dev.sergiobelda.todometer.common.data.localdatasource.ITaskLocalDataSource
import dev.sergiobelda.todometer.common.data.localdatasource.TaskChecklistItemLocalDataSource
import dev.sergiobelda.todometer.common.data.localdatasource.TaskListLocalDataSource
import dev.sergiobelda.todometer.common.data.localdatasource.TaskLocalDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val localDataSourceModule = module {
    singleOf(::TaskListLocalDataSource) bind ITaskListLocalDataSource::class
    singleOf(::TaskLocalDataSource) bind ITaskLocalDataSource::class
    singleOf(::TaskChecklistItemLocalDataSource) bind ITaskChecklistItemLocalDataSource::class
}
