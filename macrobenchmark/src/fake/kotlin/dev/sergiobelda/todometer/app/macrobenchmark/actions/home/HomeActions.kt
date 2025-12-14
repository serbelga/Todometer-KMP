package dev.sergiobelda.todometer.app.macrobenchmark.actions.home

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import dev.sergiobelda.todometer.app.macrobenchmark.actions.flingElementDownUp

fun MacrobenchmarkScope.homeScrollTasksDownUp() {
    device.wait(Until.hasObject(By.res("home:tasks_list_view")), 5_000)
    val taskLists = device.findObject(By.res("home:tasks_list_view"))
    device.flingElementDownUp(taskLists)
}
