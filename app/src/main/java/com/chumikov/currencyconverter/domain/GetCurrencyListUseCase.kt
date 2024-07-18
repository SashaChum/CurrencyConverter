package com.chumikov.currencyconverter.domain

class GetCurrencyListUseCase(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(): List<Currency> {
        return repository.getCurrencyList()
    }
}