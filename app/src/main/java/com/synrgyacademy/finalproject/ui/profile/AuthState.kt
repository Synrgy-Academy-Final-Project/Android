package com.synrgyacademy.finalproject.ui.profile

sealed interface AuthState {
    data object Loading : AuthState
    data object Success : AuthState
    data class Error(val error: String) : AuthState
}

sealed interface IsLoginState {
    data object Loading : IsLoginState
    data class Success(val isLogin: Boolean) : IsLoginState
    data class Error(val error: String) : IsLoginState
}