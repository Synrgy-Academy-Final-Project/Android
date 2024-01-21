package com.synrgyacademy.finalproject.ui.resetpassword

sealed interface ResetPasswordState {
    data object Loading : ResetPasswordState
    data class Success(val token: String) : ResetPasswordState
    data class Error(val error: String) : ResetPasswordState
}

sealed interface ChangePasswordState {
    data object Loading : ChangePasswordState
    data object Success : ChangePasswordState
    data class Error(val error: String) : ChangePasswordState
}