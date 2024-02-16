package com.synrgyacademy.data.remote.response.airport

import com.google.gson.annotations.SerializedName

data class GetDetailHistoryTransactionResponse(
    @field:SerializedName("data")
    val data: List<HistoryItem>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)
