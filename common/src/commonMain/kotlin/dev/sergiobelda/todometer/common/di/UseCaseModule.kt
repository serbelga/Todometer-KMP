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
import org.koin.dsl.module

val useCaseModule = module {
    single {
        GetTaskListsUseCase(get())
    }
    single {
        RefreshTaskListsUseCase(get())
    }
    single {
        RefreshTaskListSelectedUseCase(get(), get())
    }
    single {
        InsertTaskListUseCase(get(), get())
    }
    single {
        GetTaskUseCase(get())
    }
    single {
        GetTaskListTasksUseCase(get())
    }
    single {
        GetTaskListSelectedTasksUseCase(get(), get())
    }
    single {
        InsertTaskUseCase(get())
    }
    single {
        InsertTaskInTaskListSelectedUseCase(get(), get(), get())
    }
    single {
        UpdateTaskUseCase(get())
    }
    single {
        UpdateTaskListUseCase(get())
    }
    single {
        SetTaskDoingUseCase(get())
    }
    single {
        SetTaskDoneUseCase(get())
    }
    single {
        DeleteTaskUseCase(get())
    }
    single {
        DeleteTaskListUseCase(get())
    }
    single {
        DeleteTaskListSelectedUseCase(get(), get())
    }
    single {
        GetTaskListSelectedUseCase(get(), get())
    }
    single {
        SetTaskListSelectedUseCase(get())
    }
    single {
        GetAppThemeUseCase(get())
    }
    single {
        SetAppThemeUseCase(get())
    }
    single {
        GetTaskListUseCase(get())
    }
    single {
        UpdateTaskListNameUseCase(get())
    }
    single {
        GetTaskChecklistItemsUseCase(get())
    }
    single {
        InsertTaskChecklistItemUseCase(get())
    }
    single {
        SetTaskChecklistItemUncheckedUseCase(get())
    }
    single {
        SetTaskChecklistItemCheckedUseCase(get())
    }
    single {
        DeleteTaskChecklistItemUseCase(get())
    }
}
