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

package com.sergiobelda.todometer.common.di

import com.sergiobelda.todometer.common.usecase.DeleteTaskUseCase
import com.sergiobelda.todometer.common.usecase.GetProjectSelectedUseCase
import com.sergiobelda.todometer.common.usecase.GetProjectsUseCase
import com.sergiobelda.todometer.common.usecase.GetTaskUseCase
import com.sergiobelda.todometer.common.usecase.GetTasksUseCase
import com.sergiobelda.todometer.common.usecase.InsertProjectUseCase
import com.sergiobelda.todometer.common.usecase.InsertTaskUseCase
import com.sergiobelda.todometer.common.usecase.SetProjectSelectedUseCase
import com.sergiobelda.todometer.common.usecase.SetTaskDoingUseCase
import com.sergiobelda.todometer.common.usecase.SetTaskDoneUseCase
import com.sergiobelda.todometer.common.usecase.UpdateTaskUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single {
        GetProjectsUseCase(get())
    }
    single {
        InsertProjectUseCase(get())
    }
    single {
        GetTaskUseCase(get())
    }
    single {
        GetTasksUseCase(get())
    }
    single {
        InsertTaskUseCase(get())
    }
    single {
        UpdateTaskUseCase(get())
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
        GetProjectSelectedUseCase(get(), get())
    }
    single {
        SetProjectSelectedUseCase(get())
    }
}
