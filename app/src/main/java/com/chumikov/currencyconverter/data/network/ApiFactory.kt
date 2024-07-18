package com.chumikov.currencyconverter.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiFactory {

    private const val BASE_URL = "https://v6.exchangerate-api.com/v6/"

    private val client = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    val exchageRateApi = retrofit.create(ExchangeratesApi::class.java)

}