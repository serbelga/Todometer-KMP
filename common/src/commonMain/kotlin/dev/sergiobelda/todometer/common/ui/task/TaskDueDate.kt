package dev.sergiobelda.todometer.common.ui.task

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt

object TaskDueDate {

    /**
     * Returns due date formatted. If due date is today, it only shows HH:mm,
     * otherwise it displays dd-MM-yyyy HH:mm.
     */
    fun getDueDateFormatted(dueDate: Long): String {
        val timeZone: TimeZone = TimeZone.currentSystemDefault()

        val dueDateInstant: Instant = Instant.fromEpochMilliseconds(dueDate)
        val dueDateLocalDateTime: LocalDateTime = dueDateInstant.toLocalDateTime(timeZone)

        val todayLocalDate = Clock.System.todayAt(timeZone)

        val formattedHour = "${dueDateLocalDateTime.hour}:${dueDateLocalDateTime.minute}h"
        return if (dueDateLocalDateTime.date.compareTo(todayLocalDate) == 0) {
            formattedHour
        } else {
            // TODO: Format day and month 02d.
            "${dueDateLocalDateTime.dayOfMonth}-${dueDateLocalDateTime.monthNumber}-${dueDateLocalDateTime.year} $formattedHour"
        }
    }
}
