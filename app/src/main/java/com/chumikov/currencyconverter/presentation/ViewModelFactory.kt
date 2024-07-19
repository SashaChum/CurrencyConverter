package com.chumikov.currencyconverter.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel


inline fun <reified T : ViewModel> Fragment.getViewModel(
    crossinline provider: () -> T
) = viewModels<T> {
    object : AbstractSavedStateViewModelFactory(this@getViewModel, arguments) {
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T = provider() as T
    }
}