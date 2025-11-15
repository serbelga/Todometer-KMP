package dev.sergiobelda.todometer.app.di

import dev.sergiobelda.todometer.common.data.test.repository.FakeTaskRepository
import dev.sergiobelda.todometer.common.domain.repository.ITaskRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val benchmarkModule = module {
    singleOf(::FakeTaskRepository) bind ITaskRepository::class
}
