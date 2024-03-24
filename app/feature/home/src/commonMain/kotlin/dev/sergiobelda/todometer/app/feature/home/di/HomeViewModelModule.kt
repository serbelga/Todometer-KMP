package dev.sergiobelda.todometer.app.feature.home.di

import dev.sergiobelda.todometer.app.feature.home.ui.HomeViewModel
import org.koin.dsl.module

val homeViewModelModule = module {
    factory {
        HomeViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get())
    }
}
