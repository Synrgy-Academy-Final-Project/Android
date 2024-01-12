package com.synrgyacademy.finalproject.ui.state

import com.synrgyacademy.data.remote.response.AuthData

sealed interface LoginState {
    data object Loading : LoginState
    data class Success(val authData: AuthData) : LoginState
    data class Error(val error: String) : LoginState
}