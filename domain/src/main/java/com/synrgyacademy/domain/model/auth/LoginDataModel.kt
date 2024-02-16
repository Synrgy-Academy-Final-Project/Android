package com.synrgyacademy.domain.model.auth

data class LoginDataModel(
    val roles: List<String>,
    val fullName: String,
    val type: String,
    val email: String,
    val token: String
) {
    fun toUserData() = UserDataDataModel(
        email = email,
        token = token
    )
}
