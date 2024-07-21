package com.chumikov.currencyconverter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chumikov.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.chumikov.currencyconverter.domain.GetCurrencyListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val getCurrencyListUseCase: GetCurrencyListUseCase
) : ViewModel() {

    private val _mainScreenState = MutableStateFlow<MainScreenState>(MainScreenState.Loading)
    val mainScreenState = _mainScreenState.asStateFlow()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            try {
                val currencyList = getCurrencyListUseCase()
                _mainScreenState.value = MainScreenState.Content(currencyList = currencyList)
            } catch (e: Exception) {
                _mainScreenState.value = MainScreenState.Error
            }
        }
    }

    fun retry() {
        _mainScreenState.value = MainScreenState.Loading
        load()
    }


    fun setCurrencyFromSpinnerState(currencyFrom: String) {
        _mainScreenState.update { state ->
            if (state is MainScreenState.Content) {
                state.copy(currencyFrom = currencyFrom)
            } else { throw IllegalStateException("Wrong State") }
        }
    }

    fun setCurrencyToSpinnerState(currencyTo: String) {
        _mainScreenState.update { state ->
            if (state is MainScreenState.Content) {
                state.copy(currencyTo = currencyTo)
            } else { throw IllegalStateException("Wrong State") }
        }
    }

    fun setAmountState(amount: String) {
        _mainScreenState.update { state ->
            if (state is MainScreenState.Content) {
                state.copy(amountToCalculate = amount)
            } else { throw IllegalStateException("Wrong State") }
        }
    }

}