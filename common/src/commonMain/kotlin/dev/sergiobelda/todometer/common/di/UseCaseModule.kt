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

import dev.sergiobelda.todometer.common.usecase.DeleteProjectUseCase
import dev.sergiobelda.todometer.common.usecase.DeleteTaskUseCase
import dev.sergiobelda.todometer.common.usecase.GetAppThemeUseCase
import dev.sergiobelda.todometer.common.usecase.GetProjectSelectedTasksUseCase
import dev.sergiobelda.todometer.common.usecase.GetProjectSelectedUseCase
import dev.sergiobelda.todometer.common.usecase.GetProjectTasksUseCase
import dev.sergiobelda.todometer.common.usecase.GetProjectsUseCase
import dev.sergiobelda.todometer.common.usecase.GetTaskUseCase
import dev.sergiobelda.todometer.common.usecase.InsertProjectUseCase
import dev.sergiobelda.todometer.common.usecase.InsertTaskProjectSelectedUseCase
import dev.sergiobelda.todometer.common.usecase.InsertTaskUseCase
import dev.sergiobelda.todometer.common.usecase.RefreshProjectSelectedUseCase
import dev.sergiobelda.todometer.common.usecase.RefreshProjectsUseCase
import dev.sergiobelda.todometer.common.usecase.SetAppThemeUseCase
import dev.sergiobelda.todometer.common.usecase.SetProjectSelectedUseCase
import dev.sergiobelda.todometer.common.usecase.SetTaskDoingUseCase
import dev.sergiobelda.todometer.common.usecase.SetTaskDoneUseCase
import dev.sergiobelda.todometer.common.usecase.UpdateProjectUseCase
import dev.sergiobelda.todometer.common.usecase.UpdateTaskUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single {
        GetProjectsUseCase(get())
    }
    single {
        RefreshProjectsUseCase(get())
    }
    single {
        RefreshProjectSelectedUseCase(get(), get())
    }
    single {
        InsertProjectUseCase(get(), get())
    }
    single {
        GetTaskUseCase(get())
    }
    single {
        GetProjectTasksUseCase(get())
    }
    single {
        GetProjectSelectedTasksUseCase(get(), get())
    }
    single {
        InsertTaskUseCase(get())
    }
    single {
        InsertTaskProjectSelectedUseCase(get(), get())
    }
    single {
        UpdateTaskUseCase(get())
    }
    single {
        UpdateProjectUseCase(get())
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
        DeleteProjectUseCase(get(), get())
    }
    single {
        GetProjectSelectedUseCase(get(), get())
    }
    single {
        SetProjectSelectedUseCase(get())
    }
    single {
        GetAppThemeUseCase(get())
    }
    single {
        SetAppThemeUseCase(get())
    }
}
