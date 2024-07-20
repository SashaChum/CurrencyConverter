package com.chumikov.currencyconverter.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainScreenViewModel : ViewModel() {

    private val _mainScreenState = MutableStateFlow(MainScreenState())
    val mainScreenState = _mainScreenState.asStateFlow()


    fun setLeftSpinnerState(leftSpinnerVal: String, index: Int) {
        _mainScreenState.update { oldState ->
            oldState.copy(leftSpinnerValue = leftSpinnerVal, leftSpinnerIndex = index)
        }
    }

    fun setRightSpinnerState(rightSpinnerVal: String, index: Int) {
        _mainScreenState.update { oldState ->
            oldState.copy(rightSpinnerValue = rightSpinnerVal, rightSpinnerIndex = index)
        }
    }

    fun setAmountState(amount: String) {
        _mainScreenState.update { oldState ->
            oldState.copy(amountToCalculate = amount)
        }
    }

}