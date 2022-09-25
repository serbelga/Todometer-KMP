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

import dev.sergiobelda.todometer.common.domain.usecase.apptheme.GetAppThemeUseCase
import dev.sergiobelda.todometer.common.domain.usecase.apptheme.SetAppThemeUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.DeleteTaskUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.GetTaskListSelectedTasksUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.GetTaskListTasksUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.GetTaskUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.InsertTaskInTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.InsertTaskUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.SetTaskDoingUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.SetTaskDoneUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.UpdateTaskUseCase
import dev.sergiobelda.todometer.common.domain.usecase.taskchecklistitem.DeleteTaskChecklistItemUseCase
import dev.sergiobelda.todometer.common.domain.usecase.taskchecklistitem.GetTaskChecklistItemsUseCase
import dev.sergiobelda.todometer.common.domain.usecase.taskchecklistitem.InsertTaskChecklistItemUseCase
import dev.sergiobelda.todometer.common.domain.usecase.taskchecklistitem.SetTaskChecklistItemCheckedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.taskchecklistitem.SetTaskChecklistItemUncheckedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.DeleteTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.DeleteTaskListUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.GetTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.GetTaskListUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.GetTaskListsUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.InsertTaskListUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.RefreshTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.RefreshTaskListsUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.SetTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.UpdateTaskListNameUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.UpdateTaskListUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(::GetTaskListsUseCase)
    singleOf(::RefreshTaskListsUseCase)
    singleOf(::RefreshTaskListSelectedUseCase)
    singleOf(::InsertTaskListUseCase)
    singleOf(::GetTaskUseCase)
    singleOf(::GetTaskListTasksUseCase)
    singleOf(::GetTaskListSelectedTasksUseCase)
    singleOf(::InsertTaskUseCase)
    singleOf(::InsertTaskInTaskListSelectedUseCase)
    singleOf(::UpdateTaskUseCase)
    singleOf(::UpdateTaskListUseCase)
    singleOf(::SetTaskDoingUseCase)
    singleOf(::SetTaskDoneUseCase)
    singleOf(::DeleteTaskUseCase)
    singleOf(::DeleteTaskListUseCase)
    singleOf(::DeleteTaskListSelectedUseCase)
    singleOf(::GetTaskListSelectedUseCase)
    singleOf(::SetTaskListSelectedUseCase)
    singleOf(::GetAppThemeUseCase)
    singleOf(::SetAppThemeUseCase)
    singleOf(::GetTaskListUseCase)
    singleOf(::UpdateTaskListNameUseCase)
    singleOf(::GetTaskChecklistItemsUseCase)
    singleOf(::InsertTaskChecklistItemUseCase)
    singleOf(::SetTaskChecklistItemUncheckedUseCase)
    singleOf(::SetTaskChecklistItemCheckedUseCase)
    singleOf(::DeleteTaskChecklistItemUseCase)
}
