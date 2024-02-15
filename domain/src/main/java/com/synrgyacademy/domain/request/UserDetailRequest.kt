package com.synrgyacademy.domain.request

import com.google.gson.annotations.SerializedName

data class UserDetailRequest(
    @field:SerializedName("firstName")
    val firstName: String? = null,

    @field:SerializedName("lastName")
    val lastName: String? = null,

    @field:SerializedName("dateOfBirth")
    val dateOfBirth: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("phoneNumber")
    val phoneNumber: String? = null,

    @field:SerializedName("visa")
    val visa: String? = null,

    @field:SerializedName("passport")
    val passport: String? = null,

    @field:SerializedName("residentPermit")
    val residentPermit: String? = null,

    @field:SerializedName("nationality")
    val nationality: String? = null,

    @field:SerializedName("nik")
    val nik: String? = null,
)