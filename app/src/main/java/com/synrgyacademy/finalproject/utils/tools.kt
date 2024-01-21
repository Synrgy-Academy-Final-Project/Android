package com.synrgyacademy.finalproject.utils

fun censorEmail(email: String): String {
    val emailSplit = email.split("@")
    val emailFirst = emailSplit[0]
    val emailLast = emailSplit[1]
    val emailFirstCensored = emailFirst.replaceRange(2, emailFirst.length - 2, "*****")
    return "$emailFirstCensored@$emailLast"
}