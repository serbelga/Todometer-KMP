package dev.sergiobelda.todometer.app.feature.edittasklist.di

import dev.sergiobelda.todometer.app.feature.edittasklist.ui.EditTaskListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val editTaskListViewModelModule = module {
    factoryOf(::EditTaskListViewModel)
}
