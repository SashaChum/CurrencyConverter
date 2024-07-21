package com.chumikov.currencyconverter.domain

import com.chumikov.currencyconverter.data.repository.CurrencyRepositoryImpl
import javax.inject.Inject


class GetCurrencyListUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(): List<Currency> {
        return repository.getCurrencyList()
    }
}