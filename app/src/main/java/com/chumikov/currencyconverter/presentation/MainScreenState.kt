package com.chumikov.currencyconverter.presentation

import com.chumikov.currencyconverter.domain.Currency

sealed interface MainScreenState {

    data object Loading : MainScreenState

    data object Error : MainScreenState

    data class Content(
        val amountToCalculate: String = "",
        val currencyFrom: String = "",
        val currencyTo: String = "",
        val currencyList: List<Currency>
    ) : MainScreenState

}


