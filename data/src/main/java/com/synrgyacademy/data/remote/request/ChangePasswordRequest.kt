package com.synrgyacademy.data.remote.request

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("newPassword")
    val newPassword: String,

    @field:SerializedName("confirmationPassword")
    val confirmationPassword: String
)