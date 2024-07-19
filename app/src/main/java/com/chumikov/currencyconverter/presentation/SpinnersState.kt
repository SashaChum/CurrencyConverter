package com.chumikov.currencyconverter.presentation

data class SpinnersState(
    val leftSpinnerValue: String = "RUB",
    val rightSpinnerValue: String = "RUB",
    val currencyList: List<String> = listOf("RUB", "USD", "EUR", "GBP", "CNY", "JPY")
)
