package com.chumikov.currencyconverter.presentation

sealed interface CalculationScreenState {

    data object Loading : CalculationScreenState

    data object Error : CalculationScreenState

    data class Content(
        val currencyFrom: String,
        val currencyTo: String,
        val amount: Double,
        val calcResult: String
    ) : CalculationScreenState

}