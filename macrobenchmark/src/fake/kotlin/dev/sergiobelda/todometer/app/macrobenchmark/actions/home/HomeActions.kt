package dev.sergiobelda.todometer.app.macrobenchmark.actions.home

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import dev.sergiobelda.todometer.app.feature.home.ui.HomeElementId
import dev.sergiobelda.todometer.app.macrobenchmark.actions.flingElementDownUp

fun MacrobenchmarkScope.homeScrollTasksDownUp() {
    device.wait(Until.hasObject(By.res(HomeElementId.HomeTasksListView.id)), 10_000)
    val taskLists = device.findObject(By.res(HomeElementId.HomeTasksListView.id))
    device.flingElementDownUp(taskLists)
}
