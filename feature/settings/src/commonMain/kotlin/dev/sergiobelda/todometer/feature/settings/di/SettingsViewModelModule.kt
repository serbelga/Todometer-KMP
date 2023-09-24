package dev.sergiobelda.todometer.feature.settings.di

import dev.sergiobelda.todometer.feature.settings.ui.SettingsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val settingsViewModelModule = module {
    factoryOf(::SettingsViewModel)
}
