package com.sergiobelda.todometer.di

import com.sergiobelda.todometer.ui.addproject.AddProjectViewModel
import com.sergiobelda.todometer.ui.addtask.AddTaskViewModel
import com.sergiobelda.todometer.ui.edittask.EditTaskViewModel
import com.sergiobelda.todometer.ui.home.HomeViewModel
import com.sergiobelda.todometer.ui.projectdetail.ProjectDetailViewModel
import com.sergiobelda.todometer.ui.taskdetail.TaskDetailViewModel
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
        EditTaskViewModel(get(), get())
    }
    viewModel {
        HomeViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get(), get())
    }
    viewModel {
        ProjectDetailViewModel()
    }
    viewModel {
        TaskDetailViewModel(get())
    }
}
