package com.synrgyacademy.data.remote.response

import com.google.gson.annotations.SerializedName

data class AuthData(

    @field:SerializedName("roles")
    val roles: List<String?>? = null,

    @field:SerializedName("fullName")
    val fullName: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("email")
    val email: String? = null
)
