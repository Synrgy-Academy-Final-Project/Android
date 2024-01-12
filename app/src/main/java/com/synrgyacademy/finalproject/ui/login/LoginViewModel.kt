package com.synrgyacademy.finalproject.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgyacademy.common.Resource
import com.synrgyacademy.data.mapper.mapToAuthData
import com.synrgyacademy.domain.model.UserData
import com.synrgyacademy.domain.usecase.auth.CreateSessionUseCase
import com.synrgyacademy.domain.usecase.auth.LoginUseCase
import com.synrgyacademy.domain.usecase.auth.SaveDataProfileUseCase
import com.synrgyacademy.finalproject.ui.state.LoginState
import com.synrgyacademy.finalproject.ui.state.SaveState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val createSessionUseCase: CreateSessionUseCase,
    private val savedDataUseCase: SaveDataProfileUseCase
) : ViewModel() {

    private var _saveDataState = MutableLiveData<SaveState>()
    val saveDataState: LiveData<SaveState> get() = _saveDataState

    private var _sessionState = MutableLiveData<LoginState>()
    val sessionState: LiveData<LoginState> get() = _sessionState

    fun login(email: String, password: String) {
        loginUseCase.invoke(email, password).onEach { result ->
            when (result) {
                is Resource.Loading -> _sessionState.value = LoginState.Loading
                is Resource.Error -> _sessionState.value = LoginState.Error(result.error)
                is Resource.Success -> _sessionState.value = LoginState.Success(result.data.mapToAuthData())
            }
        }.launchIn(viewModelScope)
    }

    fun saveData(userData: UserData) =
        savedDataUseCase.invoke(userData).onEach { result ->
            when (result) {
                is Resource.Loading -> _saveDataState.value = SaveState.Loading
                is Resource.Error -> _saveDataState.value = SaveState.Error(result.error)
                is Resource.Success -> _saveDataState.value = SaveState.Success
            }
        }.launchIn(viewModelScope)

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}