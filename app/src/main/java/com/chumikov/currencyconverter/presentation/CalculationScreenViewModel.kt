package com.chumikov.currencyconverter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chumikov.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.chumikov.currencyconverter.domain.GetConvertationResultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CalculationScreenViewModel(
    private val fromCurrency: String,
    private val toCurrency: String,
    private val amount: Double
) : ViewModel() {

//    private val getConversationResultUseCase =
//        GetConvertationResultUseCase(CurrencyRepositoryImpl)
//
//    private val _calculationScreenState =
//        MutableStateFlow<CalculationScreenState>(CalculationScreenState.Loading)
//    val calculationScreenState = _calculationScreenState.asStateFlow()
//
//
//    init {
//        load()
//    }
//
//    private fun load() {
//        viewModelScope.launch {
//            try {
//                val conversationResult = getConversationResultUseCase(
//                    currencyFrom = fromCurrency,
//                    currencyTo = toCurrency,
//                    amount = amount
//                )
//                _calculationScreenState.value = CalculationScreenState.Content(
//                    calcResult = conversationResult
//                )
//
//            } catch (e: Exception) {
//                _calculationScreenState.value = CalculationScreenState.Error
//            }
//        }
//    }
//
//    fun retry() {
//        _calculationScreenState.value = CalculationScreenState.Loading
//        load()
//    }

}