package dev.sergiobelda.todometer.app.feature.edittask.di

import dev.sergiobelda.todometer.app.feature.edittask.ui.EditTaskViewModel
import org.koin.dsl.module

val editTaskViewModelModule = module {
    factory { parameters ->
        EditTaskViewModel(taskId = parameters.get(), get(), get())
    }
}
