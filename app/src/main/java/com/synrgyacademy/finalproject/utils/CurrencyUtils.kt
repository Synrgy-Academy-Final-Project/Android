package com.synrgyacademy.finalproject.utils

import java.text.NumberFormat
import java.util.Locale

object CurrencyUtils {

    fun String.toIdrWithoutRpFormat(): String {
        val value = this.toIntOrNull() ?: 0
        val format = NumberFormat.getNumberInstance(Locale("id", "ID"))
        format.isGroupingUsed = true
        return format.format(value)
    }

    fun String.toIdrFormat(): String {
        val value = this.toIntOrNull() ?: 0
        val format = NumberFormat.getNumberInstance(Locale("id", "ID"))
        format.isGroupingUsed = true
        return "Rp${format.format(value)}"
    }

    fun Int.toIdrFormatWithoutRp(): String {
        val format = NumberFormat.getNumberInstance(Locale("id", "ID"))
        format.isGroupingUsed = true
        return format.format(this)
    }

    fun Int.toIdrFormat(): String {
        val format = NumberFormat.getNumberInstance(Locale("id", "ID"))
        format.isGroupingUsed = true
        return "Rp${format.format(this)}"
    }

    fun Float.toIDR(): String {
        val format = NumberFormat.getNumberInstance(Locale("id", "ID"))
        format.isGroupingUsed = true
        return "Rp${format.format(this)}"
    }

    fun Double.toIDRWithoutRP(): String {
        val format = NumberFormat.getNumberInstance(Locale("id", "ID"))
        format.isGroupingUsed = true
        return format.format(this)
    }

    fun Double.IDRRPtoDefaultNumber(): Int {
        val format = NumberFormat.getNumberInstance(Locale("id", "ID"))
        format.isGroupingUsed = true
        return format.parse(this.toString())?.toInt() ?: 0
    }
}