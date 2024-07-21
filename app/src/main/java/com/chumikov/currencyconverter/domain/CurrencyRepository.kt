package com.chumikov.currencyconverter.domain

interface CurrencyRepository {

    suspend fun getCurrencyList(): List<Currency>

    suspend fun getCurrencyRate(
        fromCurrency: String,
        toCurrency: String,
    ): Double

}