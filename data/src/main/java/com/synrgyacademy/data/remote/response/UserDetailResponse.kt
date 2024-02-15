package com.synrgyacademy.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @field:SerializedName("data")
    val data: UserDetail? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("message")
    val message: String? = null
)