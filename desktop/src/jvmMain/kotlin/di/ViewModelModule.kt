package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ui.addtask.AddTaskViewModel
import ui.addtasklist.AddTaskListViewModel

internal val viewModelModule = module {
    factoryOf(::AddTaskViewModel)
    factoryOf(::AddTaskListViewModel)
}
