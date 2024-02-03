package com.synrgyacademy.finalproject.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.usecase.auth.IsLoginUseCase
import com.synrgyacademy.domain.usecase.auth.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val isLoginUseCase: IsLoginUseCase
) : ViewModel() {

    private var _logoutState = MutableLiveData<AuthState>()
    val logoutState: MutableLiveData<AuthState> get() = _logoutState

    private var _isLoginState = MutableLiveData<IsLoginState>()
    val isLoginState: MutableLiveData<IsLoginState> get() = _isLoginState

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

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}