package dev.sergiobelda.todometer.app.ui.main

import dev.sergiobelda.todometer.app.di.benchmarkModule
import org.koin.core.module.Module

class BenchmarkMainActivity : MainActivity() {

    override fun getModules(): List<Module> =
        super.getModules() + benchmarkModule
}
