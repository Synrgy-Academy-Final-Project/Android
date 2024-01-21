package com.synrgyacademy.finalproject.ui.profile

sealed interface AuthState {
    data object Loading : AuthState
    data object Success : AuthState
    data class Error(val error: String) : AuthState
}