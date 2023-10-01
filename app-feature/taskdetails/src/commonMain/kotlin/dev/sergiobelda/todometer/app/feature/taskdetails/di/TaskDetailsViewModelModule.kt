package dev.sergiobelda.todometer.app.feature.taskdetails.di

import dev.sergiobelda.todometer.app.feature.taskdetails.ui.TaskDetailsViewModel
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
