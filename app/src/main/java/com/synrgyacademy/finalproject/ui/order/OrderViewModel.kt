package com.synrgyacademy.finalproject.ui.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.usecase.airport.GetHistoryTransaction
import com.synrgyacademy.domain.usecase.auth.GetUserDataUseCase
import com.synrgyacademy.domain.usecase.user.GetUserByTokenUseCase
import com.synrgyacademy.finalproject.ui.passenger.PassengerState
import com.synrgyacademy.finalproject.ui.passenger.UserDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getHistoryTransaction: GetHistoryTransaction,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getUserByTokenUseCase: GetUserByTokenUseCase
) : ViewModel() {

    private val _getHistory = MutableLiveData<OrderState>()
    val getHistory: MutableLiveData<OrderState> get() = _getHistory

    private var _userData = MutableLiveData<UserDataState>()
    val userData: MutableLiveData<UserDataState> get() = _userData

    private var _getUserDataByToken = MutableLiveData<PassengerState>()
    val getUserDataByToken: MutableLiveData<PassengerState> get() = _getUserDataByToken

    fun getHistory(token: String) {
        getHistoryTransaction(token).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _getHistory.value = OrderState.Loading
                }
                is Resource.Success -> {
                    _getHistory.value = OrderState.Success(result.data)
                }
                is Resource.Error -> {
                    _getHistory.value = OrderState.Error(result.error)
                }
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

    fun getUserDataByToken(token: String) {
        getUserByTokenUseCase(token).onEach { result ->
            when (result) {
                is Resource.Loading -> _getUserDataByToken.value = PassengerState.Loading
                is Resource.Error -> _getUserDataByToken.value = PassengerState.Error(result.error)
                is Resource.Success -> _getUserDataByToken.value =
                    PassengerState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }
}