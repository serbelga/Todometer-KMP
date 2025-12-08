package dev.sergiobelda.todometer.app.macrobenchmark.baselineprofile

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.sergiobelda.todometer.app.benchmark.BuildConfig
import dev.sergiobelda.todometer.app.macrobenchmark.utils.APP_PACKAGE_NAME
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class AppStartupProfileGenerator {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() {
        baselineProfileRule.collect(
            packageName = "$APP_PACKAGE_NAME.${BuildConfig.FLAVOR}",
            includeInStartupProfile = true,
            profileBlock = MacrobenchmarkScope::startActivityAndWait
        )
    }
}
