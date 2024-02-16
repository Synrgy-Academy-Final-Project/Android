package com.synrgyacademy.finalproject.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.request.UserDetailRequest
import com.synrgyacademy.domain.usecase.auth.GetUserDataUseCase
import com.synrgyacademy.domain.usecase.auth.IsLoginUseCase
import com.synrgyacademy.domain.usecase.auth.LogoutUseCase
import com.synrgyacademy.domain.usecase.filter.GetNotificationUseCase
import com.synrgyacademy.domain.usecase.filter.SavedNotificationUseCase
import com.synrgyacademy.domain.usecase.passenger.DeleteAllPassengerUseCase
import com.synrgyacademy.domain.usecase.user.GetUserByTokenUseCase
import com.synrgyacademy.domain.usecase.user.UpdateUserDetailUseCase
import com.synrgyacademy.finalproject.ui.passenger.DeletedPassengerState
import com.synrgyacademy.finalproject.ui.passenger.UserDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val isLoginUseCase: IsLoginUseCase,
    private val getUserByTokenUseCase: GetUserByTokenUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val deleteAllPassengerUseCase: DeleteAllPassengerUseCase,
    private val updateUserDetailUseCase: UpdateUserDetailUseCase,
    private val getNotificationUseCase: GetNotificationUseCase,
    private val savedNotificationUseCase: SavedNotificationUseCase
) : ViewModel() {

    private var _logoutState = MutableLiveData<AuthState>()
    val logoutState: MutableLiveData<AuthState> get() = _logoutState

    private var _isLoginState = MutableLiveData<IsLoginState>()
    val isLoginState: MutableLiveData<IsLoginState> get() = _isLoginState

    private var _userDetailState = MutableLiveData<UserDetailState>()
    val userDetailState: MutableLiveData<UserDetailState> get() = _userDetailState

    private var _userData = MutableLiveData<UserDataState>()
    val userData: MutableLiveData<UserDataState> get() = _userData

    private var _deletedPassenger = MutableLiveData<DeletedPassengerState>()

    private var _updateUser = MutableLiveData<UpdateUserState>()

    private var _getNotification = MutableLiveData<NotificationState>()
    val getNotification: MutableLiveData<NotificationState> get() = _getNotification

    private var _savedNotification = MutableLiveData<SavedNotificationState>()

    init {
        isLogin()
    }

    fun logout() {
        logoutUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> _logoutState.value = AuthState.Loading
                is Resource.Error -> _logoutState.value = AuthState.Error(result.error)
                is Resource.Success -> _logoutState.value = AuthState.Success
            }
        }.launchIn(viewModelScope)
    }

    private fun isLogin() {
        isLoginUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> _isLoginState.value = IsLoginState.Loading
                is Resource.Error -> _isLoginState.value = IsLoginState.Error(result.error)
                is Resource.Success -> _isLoginState.value = IsLoginState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun getUserDetailByToken(token: String) {
        getUserByTokenUseCase(token).onEach { result ->
            when (result) {
                is Resource.Loading -> _userDetailState.value = UserDetailState.Loading
                is Resource.Error -> _userDetailState.value = UserDetailState.Error(result.error)
                is Resource.Success -> _userDetailState.value = UserDetailState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun getUserData() {
        getUserDataUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> _userData.value = UserDataState.Loading
                is Resource.Error -> _userData.value = UserDataState.Error(result.error)
                is Resource.Success -> _userData.value = UserDataState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun deleteAllPassenger() {
        deleteAllPassengerUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> _deletedPassenger.value = DeletedPassengerState.Loading
                is Resource.Success -> _deletedPassenger.value = DeletedPassengerState.Success
                is Resource.Error -> _deletedPassenger.value =
                    DeletedPassengerState.Error(result.error)
            }
        }.launchIn(viewModelScope)
    }

    fun updateUser(token: String, userData: UserDetailRequest) {
        updateUserDetailUseCase(token, userData).onEach { result ->
            when (result) {
                is Resource.Loading -> _updateUser.value = UpdateUserState.Loading
                is Resource.Success -> _updateUser.value = UpdateUserState.Success(result.data)
                is Resource.Error -> _updateUser.value = UpdateUserState.Error(result.error)
            }
        }.launchIn(viewModelScope)
    }

    fun getNotification() {
        getNotificationUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> _getNotification.value = NotificationState.Loading
                is Resource.Success -> _getNotification.value =
                    NotificationState.Success(result.data)

                is Resource.Error -> _getNotification.value = NotificationState.Error(result.error)
            }
        }.launchIn(viewModelScope)
    }

    fun saveNotification(isActive: Boolean) {
        savedNotificationUseCase(isActive).onEach { result ->
            when (result) {
                is Resource.Loading -> _savedNotification.value = SavedNotificationState.Loading
                is Resource.Success -> _savedNotification.value = SavedNotificationState.Success
                is Resource.Error -> _savedNotification.value =
                    SavedNotificationState.Error(result.error)
            }
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}