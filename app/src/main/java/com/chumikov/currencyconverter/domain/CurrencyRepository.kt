package com.chumikov.currencyconverter.domain

interface CurrencyRepository {

    suspend fun getCurrencyList(): List<Currency>

    suspend fun getConvertationResult(
        currencyFrom: Currency,
        currencyTo: Currency,
        amount: Double
    ): Double

}