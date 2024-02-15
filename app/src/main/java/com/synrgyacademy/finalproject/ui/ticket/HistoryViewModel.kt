package com.synrgyacademy.finalproject.ui.ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.airport.HistoryDataModel
import com.synrgyacademy.domain.usecase.airport.DeleteAllHistoryUseCase
import com.synrgyacademy.domain.usecase.airport.GetAllHistoryUseCase
import com.synrgyacademy.domain.usecase.airport.InsertHistorySearchingUseCase
import com.synrgyacademy.domain.usecase.auth.GetUserDataUseCase
import com.synrgyacademy.domain.usecase.auth.LogoutUseCase
import com.synrgyacademy.domain.usecase.passenger.DeleteAllPassengerUseCase
import com.synrgyacademy.domain.usecase.user.GetUserByTokenUseCase
import com.synrgyacademy.finalproject.ui.passenger.DeletedPassengerState
import com.synrgyacademy.finalproject.ui.passenger.PassengerState
import com.synrgyacademy.finalproject.ui.passenger.UserDataState
import com.synrgyacademy.finalproject.ui.profile.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val insertHistorySearchingUseCase: InsertHistorySearchingUseCase,
    private val getAllHistoryUseCase: GetAllHistoryUseCase,
    private val deleteAllHistoryUseCase: DeleteAllHistoryUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getUserByTokenUseCase: GetUserByTokenUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val deleteAllPassengerUseCase: DeleteAllPassengerUseCase
) : ViewModel() {

    private val _historyState = MutableLiveData<HistoryState>()
    val historyState: LiveData<HistoryState> get() = _historyState

    private val _insertHistoryState = MutableLiveData<InsertHistoryState>()

    private val _deleteHistoryState = MutableLiveData<DeleteHistoryState>()
    val deleteHistoryState: LiveData<DeleteHistoryState> get() = _deleteHistoryState

    private var _getUserDataByToken = MutableLiveData<PassengerState>()
    val getUserDataByToken: MutableLiveData<PassengerState> get() = _getUserDataByToken

    private var _userData = MutableLiveData<UserDataState>()
    val userData: MutableLiveData<UserDataState> get() = _userData

    private var _logoutState = MutableLiveData<AuthState>()

    private var _deletedPassenger = MutableLiveData<DeletedPassengerState>()

    fun getAllHistory() {
        getAllHistoryUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _historyState.value = HistoryState.Loading
                }

                is Resource.Success -> {
                    _historyState.value = HistoryState.Success(result.data)
                }

                is Resource.Error -> {
                    _historyState.value = HistoryState.Error(result.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun insertHistory(data: HistoryDataModel) {
        insertHistorySearchingUseCase(data).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _insertHistoryState.value = InsertHistoryState.Loading
                }

                is Resource.Success -> {
                    _insertHistoryState.value = InsertHistoryState.Success
                }

                is Resource.Error -> {
                    _insertHistoryState.value = InsertHistoryState.Error(result.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteAllHistory() {
        deleteAllHistoryUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _deleteHistoryState.value = DeleteHistoryState.Loading
                }

                is Resource.Success -> {
                    _deleteHistoryState.value = DeleteHistoryState.Success
                }

                is Resource.Error -> {
                    _deleteHistoryState.value = DeleteHistoryState.Error(result.error)
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

    fun expiredToken() {
        logoutUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> _logoutState.value = AuthState.Loading
                is Resource.Error -> _logoutState.value = AuthState.Error(result.error)
                is Resource.Success -> _logoutState.value = AuthState.Success
            }
        }.launchIn(viewModelScope)
    }

    fun deletedPassenger() {
        deleteAllPassengerUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> _deletedPassenger.value = DeletedPassengerState.Loading
                is Resource.Error -> _deletedPassenger.value =
                    DeletedPassengerState.Error(result.error)

                is Resource.Success -> _deletedPassenger.value = DeletedPassengerState.Success
            }
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}