package com.synrgyacademy.data.remote.response.tourism

import com.google.gson.annotations.SerializedName

data class TourismLikeResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: TourismItem
)
