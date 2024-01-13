package com.synrgyacademy.domain.model.auth

data class AuthDataModel(
    val token: String,
    val roles: List<String>,
    val fullName: String,
    val message: String,
    val type: String,
    val email: String,
)
