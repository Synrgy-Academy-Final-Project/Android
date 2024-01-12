package com.synrgyacademy.domain.model

data class AuthData(

    val token: String? = null,
    val type: String? = null,
    val fullName: String? = null,
    val email: String? = null,
    val roles: List<String?>? = null,
)