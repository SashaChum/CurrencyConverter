package com.chumikov.currencyconverter.data.network

import com.chumikov.currencyconverter.data.network.model.CurrencyListDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeratesApi {

    @GET("{apiKey}/codes")
    suspend fun getAllCurrencies(@Path("apiKey") apiKey: String): CurrencyListDto


}


