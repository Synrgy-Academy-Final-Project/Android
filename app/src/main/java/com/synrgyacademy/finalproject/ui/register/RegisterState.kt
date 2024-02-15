package com.synrgyacademy.finalproject.ui.register

import com.synrgyacademy.domain.model.auth.AuthDataModel

sealed interface RegisterState {
    data object Loading : RegisterState
    data class Success(val authData: AuthDataModel) : RegisterState
    data class Error(val error: String) : RegisterState
}