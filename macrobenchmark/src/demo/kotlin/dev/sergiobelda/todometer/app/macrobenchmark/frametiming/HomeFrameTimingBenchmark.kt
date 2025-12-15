package dev.sergiobelda.todometer.app.macrobenchmark.frametiming

import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.sergiobelda.todometer.app.macrobenchmark.actions.home.homeScrollTasksDownUp
import dev.sergiobelda.todometer.app.macrobenchmark.utils.APP_PACKAGE_NAME
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class HomeFrameTimingBenchmark {
    @get:Rule
    val macrobenchmarkRule = MacrobenchmarkRule()

    @Test
    fun measureCompilationNone() = startup(CompilationMode.None())

    @Test
    fun measureCompilationFull() = startup(CompilationMode.Full())

    @Test
    fun measureCompilationBaselineProfiles() = startup(
        CompilationMode.Partial(baselineProfileMode = BaselineProfileMode.Require)
    )

    private fun startup(compilationMode: CompilationMode) = macrobenchmarkRule.measureRepeated(
        packageName = APP_PACKAGE_NAME,
        compilationMode = compilationMode,
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.WARM,
        setupBlock = {
            pressHome()
            startActivityAndWait()
        }
    ) {
        homeScrollTasksDownUp()
    }
}
