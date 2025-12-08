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

import dev.sergiobelda.todometer.common.di.TodometerDIModule
import dev.sergiobelda.todometer.common.domain.usecase.apptheme.GetAppThemeUseCase
import dev.sergiobelda.todometer.common.domain.usecase.apptheme.SetAppThemeUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.DeleteTasksUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.GetTaskListSelectedTasksUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.GetTaskListTasksUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.GetTaskUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.InsertTaskInTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.InsertTaskUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.SetTaskDoingUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.SetTaskDoneUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.ToggleTaskPinnedValueUseCase
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
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.SetTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.UpdateTaskListNameUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.UpdateTaskListUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

data object TodometerDomainDIModule : TodometerDIModule {
    override val module: Module =
        module {
            useCase()
        }

    private fun Module.useCase() {
        singleOf(::GetTaskListsUseCase)
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
        singleOf(::DeleteTasksUseCase)
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
        singleOf(::ToggleTaskPinnedValueUseCase)
    }
}
