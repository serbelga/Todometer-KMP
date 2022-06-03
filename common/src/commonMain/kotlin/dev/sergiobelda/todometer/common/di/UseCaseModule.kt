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

import dev.sergiobelda.todometer.common.domain.usecase.DeleteTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.DeleteTaskListUseCase
import dev.sergiobelda.todometer.common.domain.usecase.DeleteTaskUseCase
import dev.sergiobelda.todometer.common.domain.usecase.GetAppThemeUseCase
import dev.sergiobelda.todometer.common.domain.usecase.GetTaskChecklistItemsUseCase
import dev.sergiobelda.todometer.common.domain.usecase.GetTaskListSelectedTasksUseCase
import dev.sergiobelda.todometer.common.domain.usecase.GetTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.GetTaskListTasksUseCase
import dev.sergiobelda.todometer.common.domain.usecase.GetTaskListUseCase
import dev.sergiobelda.todometer.common.domain.usecase.GetTaskListsUseCase
import dev.sergiobelda.todometer.common.domain.usecase.GetTaskUseCase
import dev.sergiobelda.todometer.common.domain.usecase.InsertTaskChecklistItemUseCase
import dev.sergiobelda.todometer.common.domain.usecase.InsertTaskInTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.InsertTaskListUseCase
import dev.sergiobelda.todometer.common.domain.usecase.InsertTaskUseCase
import dev.sergiobelda.todometer.common.domain.usecase.RefreshTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.RefreshTaskListsUseCase
import dev.sergiobelda.todometer.common.domain.usecase.SetAppThemeUseCase
import dev.sergiobelda.todometer.common.domain.usecase.SetTaskChecklistItemDoingUseCase
import dev.sergiobelda.todometer.common.domain.usecase.SetTaskChecklistItemDoneUseCase
import dev.sergiobelda.todometer.common.domain.usecase.SetTaskDoingUseCase
import dev.sergiobelda.todometer.common.domain.usecase.SetTaskDoneUseCase
import dev.sergiobelda.todometer.common.domain.usecase.SetTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.UpdateTaskListNameUseCase
import dev.sergiobelda.todometer.common.domain.usecase.UpdateTaskListUseCase
import dev.sergiobelda.todometer.common.domain.usecase.UpdateTaskUseCase
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
        InsertTaskInTaskListSelectedUseCase(get(), get())
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
        SetTaskChecklistItemDoingUseCase(get())
    }
    single {
        SetTaskChecklistItemDoneUseCase(get())
    }
}
