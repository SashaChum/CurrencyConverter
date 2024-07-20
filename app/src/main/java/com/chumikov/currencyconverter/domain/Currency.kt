package com.chumikov.currencyconverter.domain


data class Currency(
    val symbolCode: String,
    val fullNaming: String
) {
    override fun toString(): String {
        return "$symbolCode: $fullNaming"
    }
}

