package com.nishit.nishtrack.util

import com.google.android.material.timepicker.TimeFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class DateTimeFormatUtil {
    companion object {
        private val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yy")

        private val yearMonthFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM yyyy")

        private val timeFormatH12: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a")

        private val timeFormatH24: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm")

        fun formatDate(date: LocalDate): String {
            return dateFormat.format(date)
        }

        fun formatTime(time: LocalTime, timeFormat: Int = TimeFormat.CLOCK_24H): String {
            return when (timeFormat) {
                TimeFormat.CLOCK_24H -> timeFormatH24.format(time)
                else -> timeFormatH12.format(time)
            }
        }

        fun formatYearMonth(yearMonth: YearMonth): String {
            return yearMonthFormat.format(yearMonth)
        }
    }
}