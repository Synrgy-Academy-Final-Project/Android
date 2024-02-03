package com.synrgyacademy.finalproject.utils

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun censorEmail(email: String): String {
    val emailSplit = email.split("@")
    val emailFirst = emailSplit[0]
    val emailLast = emailSplit[1]
    val emailFirstCensored = emailFirst.replaceRange(2, emailFirst.length - 2, "*****")
    return "$emailFirstCensored@$emailLast"
}

fun numberToPrice(number: Int): String {
    val format = NumberFormat.getNumberInstance(Locale("id", "ID"))
    return "Rp${format.format(number)}"
}

fun generateDatesInString(year: Int, month: Int): List<String> {
    val dates = mutableListOf<String>()
    val calendar = Calendar.getInstance()
    calendar.set(year, month, 1)
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale("in", "ID"))

    for (i in 0 until daysInMonth) {
        dates.add(dateFormat.format(calendar.time))
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    return dates
}

fun generateDateInNumber(year: Int, month: Int): List<String> {
    val dates = mutableListOf<String>()
    val calendar = Calendar.getInstance()
    calendar.set(year, month, 1)
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    for (i in 0 until daysInMonth) {
        dates.add(dateFormat.format(calendar.time))
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    return dates
}

fun generateDatesInString(dateString: String): List<String> {
    val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val date = inputFormat.parse(dateString)
    val calendar = Calendar.getInstance()
    calendar.time = date

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)

    val dates = mutableListOf<String>()
    calendar.set(year, month, 1)
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    for (i in 0 until daysInMonth) {
        dates.add(outputFormat.format(calendar.time))
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    return dates
}

fun extractHourFromTimestamp(timestamp: String): Int {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
    val date = inputFormat.parse(timestamp)
    val calendar = Calendar.getInstance()
    calendar.time = date
    return calendar.get(Calendar.HOUR_OF_DAY)
}

fun String.convertDepartureArrivalTIme(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
    val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    val date = inputFormat.parse(this)
    return outputFormat.format(date!!)
}

fun String.convertFlightTime(): String {
    val timeParts = this.split(":")
    val hours = timeParts[0].toInt()
    val minutes = timeParts[1].toInt()
    val seconds = timeParts[2].toInt()

    return when {
        hours > 0 -> "${hours}j ${minutes}m"
        minutes > 0 -> "${minutes}m ${seconds}d"
        else -> "${seconds}d"
    }
}

fun Int.toIdrFormat(): String {
    val format = NumberFormat.getNumberInstance(Locale("id", "ID"))
    format.isGroupingUsed = true
    return "Rp${format.format(this)}"
}

fun String.toIndonesiaDateVersion(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("EEE, dd MMM", Locale("in", "ID"))
    val date = inputFormat.parse(this)
    return outputFormat.format(date!!)
}

fun String.toOldDateVersion(): String {
    val inputFormat = SimpleDateFormat("EEE, dd MMM", Locale("in", "ID"))
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = inputFormat.parse(this)

    // Set the year to the current year
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR))

    return outputFormat.format(calendar.time)
}

fun String.toDDMMYYYY() : String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val date = inputFormat.parse(this)
    return outputFormat.format(date!!)
}

fun convertDateToIndonesiaVersion(dateString: String): String {
    val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val outputFormat = SimpleDateFormat("EEE, dd MMM", Locale("in", "ID"))
    val date = inputFormat.parse(dateString)
    return outputFormat.format(date!!)
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}