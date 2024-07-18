package com.chumikov.currencyconverter.data.network.model

import com.google.gson.annotations.SerializedName

data class CurrencyListDto(

    @SerializedName("supported_codes")
    val allCurrencies: List<List<String>>
)