package com.synrgyacademy.finalproject.ui.login

import com.synrgyacademy.domain.model.auth.LoginDataModel

sealed interface LoginState {
    data object Loading : LoginState
    data class Success(val authData: LoginDataModel) : LoginState
    data class Error(val error: String) : LoginState
}

sealed interface ForgetPasswordState {
    data object Loading : ForgetPasswordState
    data object Success : ForgetPasswordState
    data class Error(val error: String) : ForgetPasswordState
}

sealed interface VerifyAccountState {
    data object Loading : VerifyAccountState
    data class Success (val authData: LoginDataModel) : VerifyAccountState
    data class Error(val error: String) : VerifyAccountState
}

sealed interface RefreshTokenState {
    data object Loading : RefreshTokenState
    data class Success (val authData: LoginDataModel) : RefreshTokenState
    data class Error(val error: String) : RefreshTokenState
}