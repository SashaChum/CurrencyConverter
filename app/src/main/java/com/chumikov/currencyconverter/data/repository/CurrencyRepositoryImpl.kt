package com.chumikov.currencyconverter.data.repository

import com.chumikov.currencyconverter.data.network.ApiFactory
import com.chumikov.currencyconverter.data.network.ExchangeratesApi
import com.chumikov.currencyconverter.domain.Currency
import com.chumikov.currencyconverter.domain.CurrencyRepository

class CurrencyRepositoryImpl(
    private val api: ExchangeratesApi = ApiFactory.exchageRateApi
) : CurrencyRepository {

    override suspend fun getCurrencyList(): List<Currency> {
        val dtoObject = api.getAllCurrencies(API_KEY)
        return dtoObject.allCurrencies.flatMap {
            listOf(Currency(symbolCode = it[0], fullNaming = it[1]))
        }
    }

    override suspend fun getConvertationResult(
        currencyFrom: Currency,
        currencyTo: Currency,
        amount: Double
    ): Double {
        TODO("Not yet implemented")
    }


    companion object {
        private const val API_KEY = "aad958292bc85ba014d0578e"
    }
}