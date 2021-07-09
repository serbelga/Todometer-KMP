package com.sergiobelda.todometer.common.di

import com.sergiobelda.todometer.common.localdatasource.IProjectLocalDataSource
import com.sergiobelda.todometer.common.localdatasource.ITaskLocalDataSource
import com.sergiobelda.todometer.common.localdatasource.ProjectLocalDataSource
import com.sergiobelda.todometer.common.localdatasource.TaskLocalDataSource
import org.koin.dsl.module

val localDataSourceModule = module {
    single<IProjectLocalDataSource> {
        ProjectLocalDataSource(get())
    }
    single<ITaskLocalDataSource> {
        TaskLocalDataSource(get())
    }
}
