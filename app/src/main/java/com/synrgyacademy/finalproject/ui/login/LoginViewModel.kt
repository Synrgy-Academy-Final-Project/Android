package com.synrgyacademy.finalproject.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.usecase.auth.ForgetPasswordUseCase
import com.synrgyacademy.domain.usecase.auth.LoginUseCase
import com.synrgyacademy.domain.usecase.auth.VerifyAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val forgetPasswordUseCase: ForgetPasswordUseCase,
    private val verifyAccountUseCase: VerifyAccountUseCase
) : ViewModel() {

    private var _sessionState = MutableLiveData<LoginState>()
    val sessionState: LiveData<LoginState> get() = _sessionState

    private var _forgotState = MutableLiveData<ForgetPasswordState>()
    val forgotState: LiveData<ForgetPasswordState> get() = _forgotState

    private var _verifyAccountState = MutableLiveData<VerifyAccountState>()
    val verifyAccountState: LiveData<VerifyAccountState> get() = _verifyAccountState

    fun login(email: String, password: String) {
        loginUseCase.invoke(email, password).onEach { result ->
            when (result) {
                is Resource.Loading -> _sessionState.value = LoginState.Loading
                is Resource.Error -> _sessionState.value = LoginState.Error(result.error)
                is Resource.Success -> _sessionState.value = LoginState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun forgotPassword(email: String) {
        forgetPasswordUseCase.invoke(email).onEach { result ->
            when (result) {
                is Resource.Loading -> _forgotState.value = ForgetPasswordState.Loading
                is Resource.Error -> _forgotState.value = ForgetPasswordState.Error(result.error)
                is Resource.Success -> _forgotState.value = ForgetPasswordState.Success
            }
        }.launchIn(viewModelScope)
    }

    fun verifyAccount(email: String, otp: String) {
        verifyAccountUseCase.invoke(email, otp).onEach { result ->
            when (result) {
                is Resource.Loading -> _verifyAccountState.value = VerifyAccountState.Loading
                is Resource.Error -> _verifyAccountState.value = VerifyAccountState.Error(result.error)
                is Resource.Success -> _verifyAccountState.value = VerifyAccountState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}