package com.chumikov.currencyconverter.di

import com.chumikov.currencyconverter.data.network.ExchangeratesApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    @ApplicationScope
    @Provides
    fun providesApi(): ExchangeratesApi {
        val baseUrl = "https://v6.exchangerate-api.com/v6/"
        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(client)
            .build()

        return retrofit.create(ExchangeratesApi::class.java)
    }
}