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

import dev.sergiobelda.todometer.common.usecase.DeleteTaskListUseCase
import dev.sergiobelda.todometer.common.usecase.DeleteTaskUseCase
import dev.sergiobelda.todometer.common.usecase.GetAppThemeUseCase
import dev.sergiobelda.todometer.common.usecase.GetTaskListSelectedTasksUseCase
import dev.sergiobelda.todometer.common.usecase.GetTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.usecase.GetTaskListTasksUseCase
import dev.sergiobelda.todometer.common.usecase.GetTaskListsUseCase
import dev.sergiobelda.todometer.common.usecase.GetTaskUseCase
import dev.sergiobelda.todometer.common.usecase.InsertTaskInTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.usecase.InsertTaskListUseCase
import dev.sergiobelda.todometer.common.usecase.InsertTaskUseCase
import dev.sergiobelda.todometer.common.usecase.RefreshTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.usecase.RefreshTaskListsUseCase
import dev.sergiobelda.todometer.common.usecase.SetAppThemeUseCase
import dev.sergiobelda.todometer.common.usecase.SetTaskDoingUseCase
import dev.sergiobelda.todometer.common.usecase.SetTaskDoneUseCase
import dev.sergiobelda.todometer.common.usecase.SetTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.usecase.UpdateTaskListUseCase
import dev.sergiobelda.todometer.common.usecase.UpdateTaskUseCase
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
        DeleteTaskListUseCase(get(), get())
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
}
