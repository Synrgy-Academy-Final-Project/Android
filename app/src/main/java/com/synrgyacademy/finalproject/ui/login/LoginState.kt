package com.synrgyacademy.finalproject.ui.login

import com.synrgyacademy.domain.model.auth.LoginDataModel

sealed interface LoginState {
    data object Loading : LoginState
    data class Success(val authData: LoginDataModel) : LoginState
    data class Error(val error: String) : LoginState
}