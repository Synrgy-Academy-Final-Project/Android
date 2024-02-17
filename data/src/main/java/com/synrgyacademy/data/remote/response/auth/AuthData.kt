package com.synrgyacademy.data.remote.response.auth

import com.google.gson.annotations.SerializedName
import com.synrgyacademy.domain.model.auth.AuthDataModel

data class AuthData(

    @field:SerializedName("roles")
    val roles: List<String>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("email")
    val email: String? = null,
) {
    fun toAuthDataModel(): AuthDataModel {
        return AuthDataModel(
            roles = roles.orEmpty(),
            message = message.orEmpty(),
            type = type.orEmpty(),
            email = email.orEmpty()
        )
    }
}
