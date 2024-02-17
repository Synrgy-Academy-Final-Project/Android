package com.synrgyacademy.domain.model.auth

data class AuthDataModel(
    val roles: List<String>,
    val message: String,
    val type: String,
    val email: String,
)