package com.sergiobelda.todometer.ui

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import com.sergiobelda.todometer.ui.addtask.AddTask
import com.sergiobelda.todometer.ui.home.Home
import com.sergiobelda.todometer.ui.taskdetail.TaskDetail
import com.sergiobelda.todometer.ui.theme.ToDometerTheme
import com.sergiobelda.todometer.ui.utils.Navigator

@Composable
fun ToDometerApp(backDispatcher: OnBackPressedDispatcher) {
    val navigator = rememberSavedInstanceState(
        saver = Navigator.saver<Destination>(backDispatcher)
    ) {
        Navigator(Destination.Home, backDispatcher)
    }
    val actions = remember(navigator) { Actions(navigator) }
    ToDometerTheme {
        Crossfade(navigator.current) { destination ->
            when (destination) {
                Destination.Home -> Home()
                is Destination.AddTask -> AddTask(
                    upPress = actions.upPress
                )
                is Destination.TaskDetail -> TaskDetail(
                    taskId = destination.taskId,
                    upPress = actions.upPress
                )
            }
        }
    }
}
