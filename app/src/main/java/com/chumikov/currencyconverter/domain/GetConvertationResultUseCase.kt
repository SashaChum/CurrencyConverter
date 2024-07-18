package com.chumikov.currencyconverter.domain

import com.chumikov.currencyconverter.data.repository.CurrencyRepositoryImpl

class GetConvertationResultUseCase(
    private val repository: CurrencyRepositoryImpl
) {
    suspend operator fun invoke(
        currencyFrom: Currency,
        currencyTo: Currency,
        amount: Double
    ): Double {
        return repository.getConvertationResult(currencyFrom, currencyTo, amount)
    }
}