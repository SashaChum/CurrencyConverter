package com.chumikov.currencyconverter.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainScreenViewModel : ViewModel() {

    private val initialMap = mapOf(LEFT_SPINNER to "RUB", RIGHT_SPINNER to "RUB")
    private val bufferMap = initialMap.toMutableMap()

    private val _selectedItems = MutableStateFlow(initialMap)
    val selectedItems = _selectedItems.asStateFlow()

    fun setValue(pair: Pair<String, String>) {
        bufferMap[pair.first] = pair.second
        _selectedItems.value = bufferMap
    }


    companion object {
        const val LEFT_SPINNER = "letSpinner"
        const val RIGHT_SPINNER = "rightSpinner"
    }

}