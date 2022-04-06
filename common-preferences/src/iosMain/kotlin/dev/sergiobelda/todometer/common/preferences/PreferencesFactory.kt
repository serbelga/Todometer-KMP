package dev.sergiobelda.todometer.common.preferences

actual object PreferencesFactory {
    actual fun createPreferences(): Preferences =
        Preferences()
}
