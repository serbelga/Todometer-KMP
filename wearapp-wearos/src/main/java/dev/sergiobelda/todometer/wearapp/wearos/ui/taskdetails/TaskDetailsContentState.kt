package dev.sergiobelda.todometer.wearapp.wearos.ui.taskdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import dev.sergiobelda.fonament.ui.FonamentContentState
import dev.sergiobelda.fonament.ui.FonamentEvent
import kotlinx.coroutines.CoroutineScope

data class TaskDetailsContentState internal constructor (
    val scalingLazyListState: ScalingLazyListState,
    val coroutineScope: CoroutineScope,
) : FonamentContentState {

    var showDeleteTaskAlertDialog by mutableStateOf(false)
        private set

    override fun handleEvent(event: FonamentEvent) {
        when (event) {
            TaskDetailsEvent.ShowDeleteTaskAlertDialog -> {
                showDeleteTaskAlertDialog = true
            }
            TaskDetailsEvent.CancelDeleteTaskAlertDialog -> {
                showDeleteTaskAlertDialog = false
            }
        }
    }
}

@Composable
fun rememberTaskDetailsContentState(
    scalingLazyListState: ScalingLazyListState = rememberScalingLazyListState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): TaskDetailsContentState = remember {
    TaskDetailsContentState(
        scalingLazyListState = scalingLazyListState,
        coroutineScope = coroutineScope,
    )
}
