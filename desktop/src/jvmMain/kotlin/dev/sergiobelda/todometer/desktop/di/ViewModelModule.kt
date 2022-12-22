package dev.sergiobelda.todometer.desktop.di

import dev.sergiobelda.todometer.desktop.ui.addtasklist.AddTaskListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val viewModelModule = module {
    factoryOf(::AddTaskListViewModel)
}
