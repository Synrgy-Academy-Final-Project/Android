package com.synrgyacademy.data.remote.response

import com.google.gson.annotations.SerializedName

data class AllPromotionResponse(

    @field:SerializedName("data")
    val data: PromotionData? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null

)