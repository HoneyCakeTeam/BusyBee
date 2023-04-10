package com.example.busybee.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateTimeUtils {

        private val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        private val outputTimeFormat = DateTimeFormatter.ofPattern("hh:mm a")
        private val outputDateFormat = DateTimeFormatter.ofPattern("dd MMMM", Locale.US)

        fun formatDateTime(dateTimeString: String): Pair<String, String> {
            val dateTime = LocalDateTime.parse(dateTimeString, inputFormat)
            val formattedTime = dateTime.format(outputTimeFormat)
            val formattedDate = dateTime.format(outputDateFormat)

            return Pair(formattedTime, formattedDate)
        }

}
