package dev.sergiobelda.todometer.app.feature.addtasklist.di

import dev.sergiobelda.todometer.app.feature.addtasklist.ui.AddTaskListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val addTaskListViewModelModule = module {
    factoryOf(::AddTaskListViewModel)
}
