package com.chumikov.currencyconverter.data.network.model

import com.google.gson.annotations.SerializedName

data class ExchangeRateDto(

    @SerializedName("conversion_rate")
    val rate: Double
)
