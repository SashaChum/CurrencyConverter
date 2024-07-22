package com.chumikov.currencyconverter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chumikov.currencyconverter.domain.GetConvertationResultUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CalculationScreenViewModel @AssistedInject constructor(
    private val getConversationResultUseCase: GetConvertationResultUseCase,
    @Assisted("param1") private val fromCurrency: String,
    @Assisted("param2") private val toCurrency: String,
    @Assisted private val amount: Double
) : ViewModel() {

    private val _calculationScreenState =
        MutableStateFlow<CalculationScreenState>(CalculationScreenState.Loading)
    val calculationScreenState = _calculationScreenState.asStateFlow()


    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            try {
                val convertationResult = getConversationResultUseCase(
                    currencyFrom = fromCurrency,
                    currencyTo = toCurrency,
                    amount = amount
                )
                _calculationScreenState.value = CalculationScreenState.Content(
                    currencyFrom = fromCurrency,
                    currencyTo = toCurrency,
                    amount = amount,
                    calcResult = convertationResult
                )

            } catch (e: Exception) {
                _calculationScreenState.value = CalculationScreenState.Error
            }
        }
    }

    fun retry() {
        _calculationScreenState.value = CalculationScreenState.Loading
        load()
    }

    @AssistedFactory
    interface Factory {
        fun get(
            @Assisted("param1") fromCurrency: String,
            @Assisted("param2") toCurrency: String,
            amount: Double
        ): CalculationScreenViewModel
    }

}