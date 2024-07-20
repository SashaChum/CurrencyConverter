package com.chumikov.currencyconverter.data.repository

import com.chumikov.currencyconverter.data.network.ApiFactory
import com.chumikov.currencyconverter.data.network.ExchangeratesApi
import com.chumikov.currencyconverter.domain.Currency
import com.chumikov.currencyconverter.domain.CurrencyRepository


object CurrencyRepositoryImpl : CurrencyRepository {

    private const val API_KEY = "aad958292bc85ba014d0578e"
    private val api: ExchangeratesApi = ApiFactory.exchageRateApi

    override suspend fun getCurrencyList(): List<Currency> {
        val dtoObject = api.getAllCurrencies(API_KEY)
        return dtoObject.allCurrencies.flatMap {
            listOf(Currency(symbolCode = it[0], fullNaming = it[1]))
        }
    }

    override suspend fun getConvertationResult(
        fromCurrency: String,
        toCurrency: String,
        amount: Double
    ): Double {
        val rateDto = api.getExchangeRate(
            apiKey = API_KEY,
            fromCurrency = fromCurrency,
            toCurrency = toCurrency,
        )
        return rateDto.rate * amount
    }

}