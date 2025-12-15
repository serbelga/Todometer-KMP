package dev.sergiobelda.todometer.app.macrobenchmark.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.sergiobelda.todometer.app.macrobenchmark.actions.home.homeScrollTasksDownUp
import dev.sergiobelda.todometer.app.macrobenchmark.utils.APP_PACKAGE_NAME
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class HomeProfileGenerator {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() {
        baselineProfileRule.collect(
            packageName = APP_PACKAGE_NAME,
            includeInStartupProfile = true,
            profileBlock = {
                pressHome()
                startActivityAndWait()
                homeScrollTasksDownUp()
            }
        )
    }
}
