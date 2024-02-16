package com.synrgyacademy.finalproject.ui.transaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.request.TransactionRequest
import com.synrgyacademy.domain.usecase.airport.AddTransactionUseCase
import com.synrgyacademy.domain.usecase.airport.GetEticketAttachFile
import com.synrgyacademy.domain.usecase.airport.GetEticketByEmail
import com.synrgyacademy.domain.usecase.airport.GetHistoryTransactionById
import com.synrgyacademy.domain.usecase.airport.GetPromotionsUseCase
import com.synrgyacademy.domain.usecase.filter.GetNotificationUseCase
import com.synrgyacademy.finalproject.ui.profile.NotificationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getPromotionsUseCase: GetPromotionsUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val getHistoryTransactionById: GetHistoryTransactionById,
    private val getEticketByEmail: GetEticketByEmail,
    private val getEticketAttachFile: GetEticketAttachFile,
    private val getNotificationUseCase: GetNotificationUseCase
) : ViewModel() {

    private var _getPromotions = MutableLiveData<PromotionState>()
    val getPromotions: MutableLiveData<PromotionState> get() = _getPromotions

    private var _addTransaction = MutableLiveData<TransactionState>()
    val addTransaction: MutableLiveData<TransactionState> get() = _addTransaction

    private var _historyTransactionById = MutableLiveData<HistoryTransactionState>()
    val historyTransactionById: MutableLiveData<HistoryTransactionState> get() = _historyTransactionById

    private var _eTicketByEmail = MutableLiveData<EticketState>()

    private var _getNotification = MutableLiveData<NotificationState>()


    private var _eTicketPDF = MutableLiveData<EticketPDFState>()
    val eTicketPDF: MutableLiveData<EticketPDFState> get() = _eTicketPDF

    val getNotification: MutableLiveData<NotificationState> get() = _getNotification

    fun getPromotion(coupon: String) {
        getPromotionsUseCase(coupon).onEach { result ->
            when (result) {
                is Resource.Loading -> _getPromotions.value = PromotionState.Loading
                is Resource.Error -> _getPromotions.value = PromotionState.Error(result.error)
                is Resource.Success -> _getPromotions.value = PromotionState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun addTransaction(
        token: String,
        transactionRequest: TransactionRequest
    ) {
        addTransactionUseCase(token, transactionRequest).onEach { result ->
            when (result) {
                is Resource.Loading -> _addTransaction.value = TransactionState.Loading
                is Resource.Error -> _addTransaction.value = TransactionState.Error(result.error)
                is Resource.Success -> _addTransaction.value = TransactionState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun historyTransactionById(token: String, idTransaction: String) {
        getHistoryTransactionById(token, idTransaction).onEach { result ->
            when (result) {
                is Resource.Loading -> _historyTransactionById.value =
                    HistoryTransactionState.Loading

                is Resource.Error -> _historyTransactionById.value =
                    HistoryTransactionState.Error(result.error)

                is Resource.Success -> _historyTransactionById.value =
                    HistoryTransactionState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun getETicketViaEmail(token: String, idTransaction: String) {
        getEticketByEmail(token, idTransaction).onEach { result ->
            when (result) {
                is Resource.Loading -> _eTicketByEmail.value = EticketState.Loading
                is Resource.Error -> _eTicketByEmail.value = EticketState.Error(result.error)
                is Resource.Success -> _eTicketByEmail.value = EticketState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun getETicketPDF(token: String, idTransaction: String) {
        getEticketAttachFile(token, idTransaction).onEach { result ->
            when (result) {
                is Resource.Loading -> _eTicketPDF.value = EticketPDFState.Loading
                is Resource.Error -> _eTicketPDF.value = EticketPDFState.Error(result.error)
                is Resource.Success -> _eTicketPDF.value = EticketPDFState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun getNotification() {
        getNotificationUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> _getNotification.value = NotificationState.Loading
                is Resource.Error -> _getNotification.value = NotificationState.Error(result.error)
                is Resource.Success -> _getNotification.value =
                    NotificationState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}