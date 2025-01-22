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

package dev.sergiobelda.todometer.wearapp.wearos.di

import dev.sergiobelda.todometer.wearapp.wearos.ui.home.HomeViewModel
import dev.sergiobelda.todometer.wearapp.wearos.ui.taskdetail.TaskDetailViewModel
import dev.sergiobelda.todometer.wearapp.wearos.ui.tasklisttasks.TaskListTasksViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModel { parameters ->
        TaskListTasksViewModel(
            taskListId = parameters.get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
    viewModel { parameters ->
        TaskDetailViewModel(taskId = parameters.get(), get(), get(), get())
    }
}
