package com.chumikov.currencyconverter.domain

import com.chumikov.currencyconverter.data.repository.CurrencyRepositoryImpl
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

class GetConvertationResultUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(
        currencyFrom: String,
        currencyTo: String,
        amount: Double
    ): String {
        val currencyRate = repository.getCurrencyRate(currencyFrom, currencyTo)
        val decimalFormat = DecimalFormat("#.###")  // сделал три знака после запятой
        decimalFormat.roundingMode = RoundingMode.HALF_EVEN  // и банковское округление
        return decimalFormat.format(currencyRate * amount)  // как некие элементы бизнес-логики
    }
}