package dev.sergiobelda.todometer.common.app

import android.app.Application
import dev.sergiobelda.todometer.common.data.database.DriverFactory
import dev.sergiobelda.todometer.common.preferences.PreferencesFactory

open class ToDometerBaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PreferencesFactory.appContext = this
        DriverFactory.appContext = this
    }
}
