package com.chumikov.currencyconverter.presentation

data class MainScreenState(
    val amountToCalculate: String = "100",
    val currencyFrom: String = "RUB",
    val currencyTo: String = "RUB",
    val currencyList: List<String> = listOf("RUB", "USD", "EUR", "GBP", "CNY", "JPY")
)
