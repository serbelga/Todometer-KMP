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

package dev.sergiobelda.todometer.di

import dev.sergiobelda.todometer.ui.MainViewModel
import dev.sergiobelda.todometer.ui.addproject.AddProjectViewModel
import dev.sergiobelda.todometer.ui.addtask.AddTaskViewModel
import dev.sergiobelda.todometer.ui.editproject.EditProjectViewModel
import dev.sergiobelda.todometer.ui.edittask.EditTaskViewModel
import dev.sergiobelda.todometer.ui.home.HomeViewModel
import dev.sergiobelda.todometer.ui.taskdetail.TaskDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        AddProjectViewModel(get())
    }
    viewModel {
        AddTaskViewModel(get())
    }
    viewModel {
        EditProjectViewModel(get(), get())
    }
    viewModel {
        EditTaskViewModel(get(), get())
    }
    viewModel {
        HomeViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    viewModel {
        TaskDetailViewModel(get())
    }
    viewModel {
        MainViewModel(get())
    }
}
