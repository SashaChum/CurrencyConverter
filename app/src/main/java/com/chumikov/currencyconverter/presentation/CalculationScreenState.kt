package com.chumikov.currencyconverter.presentation

sealed interface CalculationScreenState {

    data object Loading : CalculationScreenState

    data object Error : CalculationScreenState

    data class Content(
        val calcResult: String
    ) : CalculationScreenState
}