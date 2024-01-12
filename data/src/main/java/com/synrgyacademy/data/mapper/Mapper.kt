package com.synrgyacademy.data.mapper

import com.synrgyacademy.data.remote.response.LoginData
import com.synrgyacademy.domain.model.auth.LoginDataModel
import com.synrgyacademy.domain.model.auth.UserData


fun LoginData.toLoginDataModel(): LoginDataModel =
    LoginDataModel(
        roles = roles ?: emptyList(),
        fullName = fullName.orEmpty(),
        type = type.orEmpty(),
        email = email.orEmpty(),
        token = token.orEmpty()
    )

fun LoginDataModel.toUserData(): UserData =
    UserData(
        name = fullName,
        token = token
    )