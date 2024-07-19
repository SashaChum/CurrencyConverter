package com.chumikov.currencyconverter.domain

interface CurrencyRepository {

//    suspend fun getCurrencyList(): List<Currency>

    suspend fun getConvertationResult(
        fromCurrency: String,
        toCurrency: String,
        amount: Double
    ): String

}