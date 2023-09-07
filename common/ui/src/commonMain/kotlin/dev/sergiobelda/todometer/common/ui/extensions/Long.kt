package dev.sergiobelda.todometer.common.ui.extensions

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Return date in format: HH:mm.
 */
fun Long.timeFormat(): String {
    val localDateTime: LocalDateTime = this.toLocalDateTime()

    return "${localDateTime.hour.format(2)}:${localDateTime.minute.format(2)}h"
}

/**
 * Return date in format: dd-MM-yyyy.
 */
fun Long.dateFormat(): String {
    val localDateTime: LocalDateTime = this.toLocalDateTime()

    return "${localDateTime.dayOfMonth.format(2)}-${localDateTime.monthNumber.format(2)}-${localDateTime.year}"
}

internal fun Long.toLocalDateTime(): LocalDateTime =
    Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.UTC)
