package com.synrgyacademy.data.remote.response.airport

import com.google.gson.annotations.SerializedName

data class EticketResponse(
    @field:SerializedName("data")
    val data: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)