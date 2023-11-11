package dev.sergiobelda.todometer.app.feature.home.di

import dev.sergiobelda.todometer.app.feature.home.ui.HomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val homeViewModelModule = module {
    factoryOf(::HomeViewModel)
}
