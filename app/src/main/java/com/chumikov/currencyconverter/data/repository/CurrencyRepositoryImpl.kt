package com.chumikov.currencyconverter.data.repository

import com.chumikov.currencyconverter.data.network.ApiFactory
import com.chumikov.currencyconverter.data.network.ExchangeratesApi
import com.chumikov.currencyconverter.domain.Currency
import com.chumikov.currencyconverter.domain.CurrencyRepository
import javax.inject.Inject


class CurrencyRepositoryImpl @Inject constructor(
     private val api: ExchangeratesApi
) : CurrencyRepository {

    override suspend fun getCurrencyList(): List<Currency> {
        val dtoObject = api.getAllCurrencies(API_KEY)
        return dtoObject.allCurrencies.flatMap {
            listOf(Currency(symbolCode = it[0], fullNaming = it[1]))
        }
    }

    override suspend fun getCurrencyRate(
        fromCurrency: String,
        toCurrency: String,
    ): Double {
        val rateDto = api.getExchangeRate(
            apiKey = API_KEY,
            fromCurrency = fromCurrency,
            toCurrency = toCurrency,
        )
        return rateDto.rate
    }

    companion object {
        private const val API_KEY = "aad958292bc85ba014d0578e"
    }

}