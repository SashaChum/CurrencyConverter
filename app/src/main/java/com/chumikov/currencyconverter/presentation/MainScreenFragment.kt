package com.chumikov.currencyconverter.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chumikov.currencyconverter.R
import com.chumikov.currencyconverter.databinding.FragmentMainScreenBinding
import com.chumikov.currencyconverter.domain.Currency
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


class MainScreenFragment : Fragment() {

    @Inject
    lateinit var viewModelProvider: Provider<MainScreenViewModel>

    private val viewModel by getViewModel { viewModelProvider.get() }

    private val component by lazy {
        (requireActivity().application as MyApplication).component
    }

    private var _binding: FragmentMainScreenBinding? = null
    private val binding: FragmentMainScreenBinding
        get() = _binding ?: throw IllegalStateException("MainScreenFragment is null")


    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val baseCurrencyAmountField = binding.amountField
        val calculationButton = binding.calculationButton
        val spinnerCurrencyFrom = binding.spinnerCurrencyFrom
        val spinnerCurrencyTo = binding.spinnerCurrencyTo
        val retryButton = binding.retryButton
        retryButton.setOnClickListener { viewModel.retry() }

        lifecycleScope.launch {
            viewModel.mainScreenState.collect { state ->
                binding.spinnerCurrencyFromLayout.isVisible = state is MainScreenState.Content
                binding.spinnerCurrencyToLayout.isVisible = state is MainScreenState.Content
                spinnerCurrencyFrom.isVisible = state is MainScreenState.Content
                spinnerCurrencyTo.isVisible = state is MainScreenState.Content
                baseCurrencyAmountField.isVisible = state is MainScreenState.Content
                calculationButton.isVisible = state is MainScreenState.Content
                binding.loader.isVisible = state is MainScreenState.Loading
                retryButton.isVisible = state is MainScreenState.Error

                if (state is MainScreenState.Content) {
                    val adapterCurrencyFrom = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        state.currencyList
                    )
                    val adapterCurrencyTo = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        state.currencyList
                    )
                    spinnerCurrencyFrom.setAdapter(adapterCurrencyFrom)
                    spinnerCurrencyTo.setAdapter(adapterCurrencyTo)

                    spinnerCurrencyFrom.setOnItemClickListener { parent, _, position, _ ->
                        val selectedItem = parent.getItemAtPosition(position) as Currency
                        val symbolCode = selectedItem.symbolCode
                        spinnerCurrencyFrom.setText(symbolCode)
                        viewModel.setCurrencyFromSpinnerState(symbolCode)
                    }
                    spinnerCurrencyFrom.setOnDismissListener {
                        val currentValue = spinnerCurrencyFrom.text.toString()
                        viewModel.setCurrencyFromSpinnerState(currentValue)
                    }

                    spinnerCurrencyTo.setOnItemClickListener { parent, _, position, _ ->
                        val selectedItem = parent.getItemAtPosition(position) as Currency
                        val symbolCode = selectedItem.symbolCode
                        spinnerCurrencyTo.setText(symbolCode)
                        viewModel.setCurrencyToSpinnerState(symbolCode)
                    }
                    spinnerCurrencyTo.setOnDismissListener {
                        val currentValue = spinnerCurrencyTo.text.toString()
                        viewModel.setCurrencyToSpinnerState(currentValue)
                    }
                }
            }
        }

        calculationButton.setOnClickListener {
            val amountField = baseCurrencyAmountField.text.toString()
            val fromCurrencyVal = spinnerCurrencyFrom.text.toString()
            val toCurrencyVal = spinnerCurrencyTo.text.toString()

            val state = viewModel.mainScreenState.value   // текущий стейт
            if (state is MainScreenState.Content) {
                val actualCurrencySymbols = state.currencyList.map {
                    it.symbolCode
                }
                when {
                    amountField.isEmpty() || fromCurrencyVal.isEmpty() || toCurrencyVal.isEmpty() -> {
                        makeToast(getString(R.string.empty_field_warning))
                    }

                    fromCurrencyVal == toCurrencyVal -> {
                        makeToast(getString(R.string.same_currencies_warning))
                    }

                    fromCurrencyVal !in actualCurrencySymbols || toCurrencyVal !in actualCurrencySymbols -> {
                        makeToast(getString(R.string.wrong_currency_warning))
                    }

                    else -> {
                        viewModel.setAmountState(amountField)  // обновление стейта
                        val newState = viewModel.mainScreenState.value
                        if (newState is MainScreenState.Content) {
                            findNavController().navigate(
                                MainScreenFragmentDirections
                                    .actionMainScreenFragmentToCalculationScreenFragment(
                                        newState.currencyFrom,
                                        newState.currencyTo,
                                        newState.amountToCalculate
                                    )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun makeToast(text: String) {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_SHORT
        ).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}