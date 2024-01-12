package com.synrgyacademy.finalproject.ui.state

sealed interface SaveState {
    data object Loading : SaveState
    data object Success : SaveState
    data class Error(val error: String) : SaveState
}