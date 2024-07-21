package com.chumikov.currencyconverter.data.network

import com.chumikov.currencyconverter.data.network.dto.CurrencyListDto
import com.chumikov.currencyconverter.data.network.dto.ExchangeRateDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRatesApi {

    @GET("{apiKey}/codes")
    suspend fun getAllCurrencies(@Path("apiKey") apiKey: String): CurrencyListDto

    @GET("{apiKey}/pair/{from}/{to}")
    suspend fun getExchangeRate(
        @Path("apiKey") apiKey: String,
        @Path("from") fromCurrency: String,
        @Path("to") toCurrency: String
    ) : ExchangeRateDto

}


