package com.chumikov.currencyconverter.presentation

data class MainScreenState(
    val amountToCalculate: String = "100",
    val leftSpinnerValue: String = "RUB",
    val leftSpinnerIndex: Int = 0,
    val rightSpinnerValue: String = "RUB",
    val rightSpinnerIndex: Int = 0,
    val currencyList: List<String> = listOf("RUB", "USD", "EUR", "GBP", "CNY", "JPY")
)
