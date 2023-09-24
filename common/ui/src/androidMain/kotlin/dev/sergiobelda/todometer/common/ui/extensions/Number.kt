package dev.sergiobelda.todometer.common.ui.extensions

actual fun Int.format(digits: Int): String = "%0${digits}d".format(this)
