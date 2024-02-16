package com.synrgyacademy.data.remote.response.auth

import com.google.gson.annotations.SerializedName

data class VerifiedOTP(
    @field:SerializedName("data")
    val data: DataVerifiedOTP? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class DataVerifiedOTP(
    @field:SerializedName("token")
    val token: String? = null
)