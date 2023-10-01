package dev.sergiobelda.todometer.common.ui.extensions

actual fun Int.format(digits: Int): String = String.format("%0${digits}d", this)
