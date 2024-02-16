package com.synrgyacademy.finalproject.ui.passenger

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.passenger.PassengerDataModel
import com.synrgyacademy.domain.usecase.auth.GetUserDataUseCase
import com.synrgyacademy.domain.usecase.auth.LogoutUseCase
import com.synrgyacademy.domain.usecase.passenger.DeleteAllPassengerUseCase
import com.synrgyacademy.domain.usecase.passenger.GetAllPassengerUseCase
import com.synrgyacademy.domain.usecase.passenger.GetPassengerByTypeUseCase
import com.synrgyacademy.domain.usecase.passenger.InsertPassengerUseCase
import com.synrgyacademy.domain.usecase.passenger.UpdatePassengerUseCase
import com.synrgyacademy.domain.usecase.user.GetUserByTokenUseCase
import com.synrgyacademy.finalproject.ui.profile.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PassengerViewModel @Inject constructor(
    private val getUserByTokenUseCase: GetUserByTokenUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val insertPassengerUseCase: InsertPassengerUseCase,
    private val getPassengerByTypeUseCase: GetPassengerByTypeUseCase,
    private val getAllPassengerUseCase: GetAllPassengerUseCase,
    private val updatePassengerUseCase: UpdatePassengerUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val deleteAllPassengerUseCase: DeleteAllPassengerUseCase
) : ViewModel() {

    private var _getUserDataByToken = MutableLiveData<PassengerState>()
    val getUserDataByToken: MutableLiveData<PassengerState> get() = _getUserDataByToken

    private var _userData = MutableLiveData<UserDataState>()
    val userData: MutableLiveData<UserDataState> get() = _userData

    private var _insertPassenger = MutableLiveData<InsertPassengerState>()

    private var _getUserByType = MutableLiveData<PassengerLocalState>()
    val getUserByType: MutableLiveData<PassengerLocalState> get() = _getUserByType

    private var _logoutState = MutableLiveData<AuthState>()

    private var _allPassenger = MutableLiveData<AllPassengerLocalState>()
    val allPassenger: MutableLiveData<AllPassengerLocalState> get() = _allPassenger

    private var _deletedPassenger = MutableLiveData<DeletedPassengerState>()

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

    fun getUserData() {
        getUserDataUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> _userData.value = UserDataState.Loading
                is Resource.Error -> _userData.value = UserDataState.Error(result.error)
                is Resource.Success -> _userData.value = UserDataState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun insertPassenger(data: PassengerDataModel) {
        insertPassengerUseCase(data).onEach { result ->
            when (result) {
                is Resource.Loading -> _insertPassenger.value = InsertPassengerState.Loading
                is Resource.Error -> _insertPassenger.value = InsertPassengerState.Error(result.error)
                is Resource.Success -> _insertPassenger.value = InsertPassengerState.Success
            }
        }.launchIn(viewModelScope)
    }

    fun getAllPassenger() {
        getAllPassengerUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> _allPassenger.value = AllPassengerLocalState.Loading
                is Resource.Error -> _allPassenger.value = AllPassengerLocalState.Error(result.error)
                is Resource.Success -> _allPassenger.value = AllPassengerLocalState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun getPassengerByType(type: String) {
        getPassengerByTypeUseCase(type).onEach { result ->
            when (result) {
                is Resource.Loading -> _getUserByType.value = PassengerLocalState.Loading
                is Resource.Error -> _getUserByType.value = PassengerLocalState.Error(result.error)
                is Resource.Success -> _getUserByType.value = PassengerLocalState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun updatePassenger(data: PassengerDataModel) {
        updatePassengerUseCase(data).onEach { result ->
            when (result) {
                is Resource.Loading -> _insertPassenger.value = InsertPassengerState.Loading
                is Resource.Error -> _insertPassenger.value = InsertPassengerState.Error(result.error)
                is Resource.Success -> _insertPassenger.value = InsertPassengerState.Success
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
                is Resource.Error -> _deletedPassenger.value = DeletedPassengerState.Error(result.error)
                is Resource.Success -> _deletedPassenger.value = DeletedPassengerState.Success
            }
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}