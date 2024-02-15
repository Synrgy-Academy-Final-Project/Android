package com.synrgyacademy.domain.utils

import com.synrgyacademy.domain.model.auth.AuthDataModel
import com.synrgyacademy.domain.model.auth.LoginDataModel

fun AuthDataModel.toLoginDataModel(): LoginDataModel =
    LoginDataModel(
        roles = roles,
        fullName = fullName,
        type = type,
        email = email,
        token = token
    )