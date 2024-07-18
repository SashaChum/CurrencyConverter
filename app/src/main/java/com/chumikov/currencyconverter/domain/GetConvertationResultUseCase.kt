package com.chumikov.currencyconverter.domain

class GetConvertationResultUseCase(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(
        currencyFrom: Currency,
        currencyTo: Currency,
        amount: Double
    ): Double {
        return repository.getConvertationResult(currencyFrom, currencyTo, amount)
    }
}