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

    private val getConversationResultUseCase =
        GetConvertationResultUseCase(CurrencyRepositoryImpl)


    private val _mainScreenState =
        MutableStateFlow<CalculationScreenState>(CalculationScreenState.Loading)
    val mainScreenState = _mainScreenState.asStateFlow()


    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            try {
                val conversationResult = getConversationResultUseCase(
                    currencyFrom = fromCurrency,
                    currencyTo = toCurrency,
                    amount = amount
                )
                _mainScreenState.value = CalculationScreenState.Content(
                    calcResult = conversationResult
                )

            } catch (e: Exception) {
                _mainScreenState.value = CalculationScreenState.Error
            }
        }
    }

    fun retry() {
        _mainScreenState.value = CalculationScreenState.Loading
        load()
    }

}