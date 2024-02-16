package com.synrgyacademy.finalproject.utils

import java.text.SimpleDateFormat
import java.util.Locale

object StringUtils {
    fun String.shortenString(length: Int): String {
        return if (this.length > length) {
            "${this.substring(0, length)}..."
        } else {
            this
        }
    }

    fun String.cleanAndUppercase(): String {
        val cleaned = this.replace("[^a-zA-Z]".toRegex(), " ")
        return cleaned.uppercase()
    }

    fun String.censorEmail(): String {
        val emailSplit = this.split("@")
        val emailFirst = emailSplit[0]
        val emailLast = emailSplit[1]
        if (emailFirst.length >= 3) {
            val emailFirstCensored = emailFirst.replaceRange(1, 2, "*")
            return "$emailFirstCensored@$emailLast"
        }
        return this
    }
    
    fun String.convertDepartureArrivalTime(): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        val date = inputFormat.parse(this)
        return outputFormat.format(date!!)
    }

    fun String.yyyyMMddTHHmmssZtoDDMMMYYYY(): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date = inputFormat.parse(this)
        return outputFormat.format(date!!)
    }

    fun String.yyyyMMddTHHmmssZtoddMMYYYY(): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        val date = inputFormat.parse(this)
        return outputFormat.format(date!!)
    }
    
    fun String.yyyyMMddTHHmmssZtoHHmmss(): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

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
}