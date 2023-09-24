package dev.sergiobelda.todometer.common.ui.extensions

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@OptIn(ExperimentalMaterial3Api::class)
internal val TimePickerState.selectedTimeMillis: Long
    get() = this.hour.hours.inWholeMilliseconds + this.minute.minutes.inWholeMilliseconds
