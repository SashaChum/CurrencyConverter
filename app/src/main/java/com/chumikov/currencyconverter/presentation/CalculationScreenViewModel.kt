package com.chumikov.currencyconverter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chumikov.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.chumikov.currencyconverter.domain.GetConvertationResultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CalculationScreenViewModel(
    val fromCurrency: String,
    val toCurrency: String,
    val amount: Double

) : ViewModel() {

    private val getConvertationResultUseCase =
        GetConvertationResultUseCase(CurrencyRepositoryImpl())


    private val _mainScreenState =
        MutableStateFlow<CalculationScreenState>(CalculationScreenState.Loading)
    val mainScreenState = _mainScreenState.asStateFlow()


    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            try {
                val convertationResult = getConvertationResultUseCase(
                    fromCurrency,
                    toCurrency,
                    amount
                )
                _mainScreenState.value = CalculationScreenState.Content(
                    calcResult = convertationResult
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