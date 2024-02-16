package com.synrgyacademy.finalproject.ui.profile

import com.synrgyacademy.domain.model.airport.UserData
import com.synrgyacademy.domain.model.airport.UserDetailDataModel

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

sealed interface UserDetailState {
    data object Loading : UserDetailState
    data class Success(val data: UserData) : UserDetailState
    data class Error(val error: String) : UserDetailState
}

sealed interface UpdateUserState {
    data object Loading : UpdateUserState
    data class Success(val data: UserDetailDataModel) : UpdateUserState
    data class Error(val error: String) : UpdateUserState
}

sealed interface NotificationState{
    data object Loading : NotificationState
    data class Success(val data: Boolean) : NotificationState
    data class Error(val error: String) : NotificationState
}

sealed interface SavedNotificationState{
    data object Loading : SavedNotificationState
    data object Success : SavedNotificationState
    data class Error(val error: String) : SavedNotificationState
}