package com.chumikov.currencyconverter.data.network.dto_classes

import com.google.gson.annotations.SerializedName

data class CurrencyListDto(

    @SerializedName("supported_codes")
    val allCurrencies: List<List<String>>
)