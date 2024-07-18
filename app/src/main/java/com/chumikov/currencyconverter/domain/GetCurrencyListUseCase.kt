package com.chumikov.currencyconverter.domain

import com.chumikov.currencyconverter.data.repository.CurrencyRepositoryImpl

class GetCurrencyListUseCase(
    private val repository: CurrencyRepositoryImpl
) {
    suspend operator fun invoke(): List<Currency> {
        return repository.getCurrencyList()
    }
}