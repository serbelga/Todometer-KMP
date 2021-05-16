package com.sergiobelda.todometer.common.di

import com.sergiobelda.todometer.common.datasource.IProjectLocalDatabaseSource
import com.sergiobelda.todometer.common.datasource.ITagLocalDatabaseSource
import com.sergiobelda.todometer.common.datasource.ITaskLocalDatabaseSource
import com.sergiobelda.todometer.common.datasource.ProjectLocalDatabaseSource
import com.sergiobelda.todometer.common.datasource.TagLocalDatabaseSource
import com.sergiobelda.todometer.common.datasource.TaskLocalDatabaseSource
import org.koin.dsl.module

val databaseSourceModule = module {
    single<IProjectLocalDatabaseSource> {
        ProjectLocalDatabaseSource(get())
    }
    single<ITaskLocalDatabaseSource> {
        TaskLocalDatabaseSource(get())
    }
    single<ITagLocalDatabaseSource> {
        TagLocalDatabaseSource(get())
    }
}
