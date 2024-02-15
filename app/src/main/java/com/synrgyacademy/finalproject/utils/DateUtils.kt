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
            val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM", Locale("in", "ID"))
            date.format(formatter)
        } else {
            val calendar = Calendar.getInstance()
            val date = calendar.time
            val formatter = SimpleDateFormat("EEE, dd MMM", Locale("in", "ID"))
            formatter.format(date)
        }
    }

    fun getCurrentDDMMYYYY(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale("in", "ID"))
            date.format(formatter)
        } else {
            val calendar = Calendar.getInstance()
            val date = calendar.time
            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale("in", "ID"))
            formatter.format(date)
        }
    }

    fun getCurrentYYYYMMDDHHMMSS(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Calendar.getInstance().time
        return dateFormat.format(date)
    }

    fun String.DDMMYYYYStriptoEEEddMMM(): String {
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEE, dd MMM", Locale("in", "ID"))
        val date = inputFormat.parse(this)
        return outputFormat.format(date!!)
    }

    fun String.DDMMYYYYStripToDDMMMYYYY() : String{
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = inputFormat.parse(this)
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return outputFormat.format(date!!)
    }

    fun String.toEEEDDMMMYYYY(): String {
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(this)
        return outputFormat.format(date!!)
    }
}