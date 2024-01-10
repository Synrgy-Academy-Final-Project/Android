package com.synrgyacademy.finalproject.utils

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

object DateUtils {
    // ** get current date in EEE, dd MMM format
    fun getCurrentDateEEEDDMMM(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM")
            date.format(formatter)
        } else {
            val calendar = Calendar.getInstance()
            val date = calendar.time
            val formatter = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
            formatter.format(date)
        }
    }
}