package com.synrgyacademy.domain.model.auth

data class LoginDataModel(
    val roles: List<String>,
    val type: String,
    val email: String,
    val token: String,
    val refreshToken: String
) {
    fun toUserData() = UserDataDataModel(
        email = email,
        token = token
    )
}
