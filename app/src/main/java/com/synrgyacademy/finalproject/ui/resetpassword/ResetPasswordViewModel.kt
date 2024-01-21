package com.synrgyacademy.finalproject.ui.resetpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.usecase.auth.ForgetPasswordUseCase
import com.synrgyacademy.domain.usecase.auth.NewPasswordUseCase
import com.synrgyacademy.domain.usecase.auth.VerifiedOTPUseCase
import com.synrgyacademy.finalproject.ui.login.ForgetPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val verifiedOTPUseCase: VerifiedOTPUseCase,
    private val forgotPasswordUseCase: ForgetPasswordUseCase,
    private val newPasswordUseCase: NewPasswordUseCase
) : ViewModel() {

    private var _resetState = MutableLiveData<ResetPasswordState>()
    val resetState: MutableLiveData<ResetPasswordState> get() = _resetState

    private var _resendOTP = MutableLiveData<ForgetPasswordState>()
    val resendOTPState: LiveData<ForgetPasswordState> get() = _resendOTP

    private var _changePassword = MutableLiveData<ChangePasswordState>()
    val changePasswordState: LiveData<ChangePasswordState> get() = _changePassword

    fun verifiedOTP(email: String, otp: String) {
        verifiedOTPUseCase.invoke(email, otp).onEach { result ->
            when (result) {
                is Resource.Loading -> _resetState.value = ResetPasswordState.Loading
                is Resource.Error -> _resetState.value = ResetPasswordState.Error(result.error)
                is Resource.Success -> _resetState.value = ResetPasswordState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun resendOTP(email: String) {
        forgotPasswordUseCase.invoke(email).onEach { result ->
            when (result) {
                is Resource.Loading -> _resendOTP.value = ForgetPasswordState.Loading
                is Resource.Error -> _resendOTP.value = ForgetPasswordState.Error(result.error)
                is Resource.Success -> _resendOTP.value = ForgetPasswordState.Success
            }
        }.launchIn(viewModelScope)
    }

    fun changePassword(email: String, token: String, newPassword: String, confirmationPassword: String) {
        newPasswordUseCase.invoke(email, token, newPassword, confirmationPassword).onEach { result ->
            when (result) {
                is Resource.Loading -> _changePassword.value = ChangePasswordState.Loading
                is Resource.Error -> _changePassword.value = ChangePasswordState.Error(result.error)
                is Resource.Success -> _changePassword.value = ChangePasswordState.Success
            }
        }.launchIn(viewModelScope)
    }
}