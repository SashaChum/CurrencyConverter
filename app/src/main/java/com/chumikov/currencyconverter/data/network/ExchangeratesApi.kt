package com.chumikov.currencyconverter.data.network

import com.chumikov.currencyconverter.data.network.dto_classes.CurrencyListDto
import com.chumikov.currencyconverter.data.network.dto_classes.ExchangeRateDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeratesApi {

    @GET("{apiKey}/codes")
    suspend fun getAllCurrencies(@Path("apiKey") apiKey: String): CurrencyListDto

    @GET("{apiKey}/pair/{from}/{to}")
    suspend fun getExchangeRate(
        @Path("apiKey") apiKey: String,
        @Path("from") fromCurrency: String,
        @Path("to") toCurrency: String
    ) : ExchangeRateDto

}


