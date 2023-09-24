package dev.sergiobelda.todometer.feature.addtasklist.di

import dev.sergiobelda.todometer.feature.addtasklist.ui.AddTaskListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val addTaskListViewModelModule = module {
    factoryOf(::AddTaskListViewModel)
}
