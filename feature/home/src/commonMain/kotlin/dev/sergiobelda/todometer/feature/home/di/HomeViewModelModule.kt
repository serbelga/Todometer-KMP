package dev.sergiobelda.todometer.feature.home.di

import dev.sergiobelda.todometer.feature.home.ui.HomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val homeViewModelModule = module {
    factoryOf(::HomeViewModel)
}
