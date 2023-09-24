package dev.sergiobelda.todometer.feature.taskdetails.di

import dev.sergiobelda.todometer.feature.taskdetails.ui.TaskDetailsViewModel
import org.koin.dsl.module

val taskDetailsViewModelModule = module {
    factory { parameters ->
        TaskDetailsViewModel(
            taskId = parameters.get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}
