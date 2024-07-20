package com.chumikov.currencyconverter.presentation

import com.chumikov.currencyconverter.domain.Currency

sealed interface MainScreenState {

    data object Loading : MainScreenState

    data object Error : MainScreenState

    data class Content(
        val amountToCalculate: String = "100",
        val currencyFrom: String = "RUB",
        val currencyTo: String = "RUB",
        val currencyList: List<Currency>
    ) : MainScreenState

}


