package com.sergiobelda.todometer.ui

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.sergiobelda.todometer.ui.utils.Navigator
import kotlinx.android.parcel.Parcelize

sealed class Destination : Parcelable {
    @Parcelize
    object Home : Destination()

    @Parcelize
    object AddTask : Destination()

    @Immutable
    @Parcelize
    data class TaskDetail(val taskId: Long) : Destination()
}

class Actions(navigator: Navigator<Destination>) {
    val selectTask: (Long) -> Unit = { taskId: Long ->
        navigator.navigate(Destination.TaskDetail(taskId))
    }
    val upPress: () -> Unit = {
        navigator.back()
    }
}
