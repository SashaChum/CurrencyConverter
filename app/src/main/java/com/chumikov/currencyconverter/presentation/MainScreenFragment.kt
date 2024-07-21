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
import androidx.lifecycle.ViewModelProvider
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
                Log.d("Inspection", "collect invoke")
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
                    spinnerCurrencyTo.setOnItemClickListener { parent, _, position, _ ->
                        val selectedItem = parent.getItemAtPosition(position) as Currency
                        val symbolCode = selectedItem.symbolCode
                        spinnerCurrencyTo.setText(symbolCode)
                        viewModel.setCurrencyToSpinnerState(symbolCode)
                    }
                }
            }
        }

        calculationButton.setOnClickListener {
            val amountField = baseCurrencyAmountField.text.toString()
            val fromCurrencyVal = spinnerCurrencyFrom.text.toString()
            val toCurrencyVal = spinnerCurrencyTo.text.toString()

            with(viewModel.mainScreenState.value) {  // текущий стейт
                this as MainScreenState.Content
                val actualCurrencySymbols = currencyList.map {
                    it.symbolCode
                }
                when {
                    "" in listOf(amountField, fromCurrencyVal, toCurrencyVal) -> {
                        makeToast(getString(R.string.empty_field_warning))
                    }
                    fromCurrencyVal == toCurrencyVal -> {
                        makeToast(getString(R.string.same_currencies_warning))
                    }
                    listOf(
                        fromCurrencyVal.length != 3,
                        toCurrencyVal.length != 3,
                        fromCurrencyVal !in actualCurrencySymbols,
                        toCurrencyVal !in actualCurrencySymbols
                    ).any { it } -> {
                        makeToast(getString(R.string.wrong_currency_warning))
                    }
                    else -> {
                        viewModel.setAmountState(amountField)  // обновление стейта
                        val newState = viewModel.mainScreenState.value as MainScreenState.Content
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