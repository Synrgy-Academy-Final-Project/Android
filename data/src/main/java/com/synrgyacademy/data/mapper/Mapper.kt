package com.synrgyacademy.data.mapper

import com.synrgyacademy.domain.model.AuthData

fun AuthData.mapToAuthData() = com.synrgyacademy.data.remote.response.AuthData(
    token = token,
    type = type,
    fullName = fullName,
    email = email,
    roles = roles
)

fun com.synrgyacademy.data.remote.response.AuthData.mapToDomain() = AuthData(
    token = token,
    type = type,
    fullName = fullName,
    email = email,
    roles = roles
)