package com.chumikov.currencyconverter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class CalcScreenVMFactoryTest(
    private val fromCurrency: String,
    private val toCurrency: String,
    private val amount: Double
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CalculationScreenViewModel(
            fromCurrency,
            toCurrency,
            amount
        ) as T
    }
}