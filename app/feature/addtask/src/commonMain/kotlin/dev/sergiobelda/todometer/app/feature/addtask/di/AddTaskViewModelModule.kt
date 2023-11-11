package dev.sergiobelda.todometer.app.feature.addtask.di

import dev.sergiobelda.todometer.app.feature.addtask.ui.AddTaskViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val addTaskViewModelModule = module {
    factoryOf(::AddTaskViewModel)
}
