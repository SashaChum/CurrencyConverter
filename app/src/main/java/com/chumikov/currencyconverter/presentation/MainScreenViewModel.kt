package com.chumikov.currencyconverter.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainScreenViewModel : ViewModel() {

    private val _spinnersState = MutableStateFlow(SpinnersState())
    val spinnersState = _spinnersState.asStateFlow()


    fun setLeftSpinnerState(leftSpinnerVal: String) {
        _spinnersState.update { oldState ->
            oldState.copy(leftSpinnerValue = leftSpinnerVal)
        }
    }

    fun setRightSpinnerState(rightSpinnerVal: String) {
        _spinnersState.update { oldState ->
            oldState.copy(rightSpinnerValue = rightSpinnerVal)
        }
    }

}