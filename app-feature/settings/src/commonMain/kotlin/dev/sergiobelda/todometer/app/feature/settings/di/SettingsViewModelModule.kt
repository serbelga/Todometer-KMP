package dev.sergiobelda.todometer.app.feature.settings.di

import dev.sergiobelda.todometer.app.feature.settings.ui.SettingsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val settingsViewModelModule = module {
    factoryOf(::SettingsViewModel)
}
