package com.chumikov.currencyconverter.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chumikov.currencyconverter.databinding.FragmentMainScreenBinding
import com.chumikov.currencyconverter.domain.Currency
import kotlinx.coroutines.launch
import java.lang.IllegalStateException


class MainScreenFragment : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding: FragmentMainScreenBinding
        get() = _binding ?: throw IllegalStateException("MainScreenFragment is null")

    private val viewModel by lazy {
        ViewModelProvider(this)[MainScreenViewModel::class.java]
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

        Log.d("Inspection", "onViewCreated")

        val editText = binding.editTextField
        val calculationButton = binding.calculationButton
        val spinnerCurrencyFrom = binding.spinnerCurrencyFrom
        val spinnerCurrencyTo = binding.spinnerCurrencyTo
        val retryButton = binding.retryButton
        retryButton.setOnClickListener { viewModel.retry() }

        lifecycleScope.launch {
            viewModel.mainScreenState.collect { state ->
                spinnerCurrencyFrom.isInvisible = state !is MainScreenState.Content
                spinnerCurrencyTo.isInvisible = state !is MainScreenState.Content
                editText.isInvisible = state !is MainScreenState.Content
                calculationButton.isInvisible = state !is MainScreenState.Content
                binding.loader.isInvisible = state !is MainScreenState.Loading
                retryButton.isInvisible = state !is MainScreenState.Error

                if (state is MainScreenState.Content) {

                    editText.setText(state.amountToCalculate)

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
                        Log.d("Inspection", "currencyFromSpinner selected Item: $selectedItem")
                        viewModel.setCurrencyFromSpinnerState(selectedItem.symbolCode)
                    }
                    spinnerCurrencyTo.setOnItemClickListener { parent, _, position, _ ->
                        val selectedItem = parent.getItemAtPosition(position) as Currency
                        Log.d("Inspection", "currencyToSpinner selected Item: $selectedItem")
                        viewModel.setCurrencyToSpinnerState(selectedItem.symbolCode)
                    }

                    calculationButton.setOnClickListener {
                        viewModel.setAmountState(editText.text.toString())
                        val currencFrom = state.currencyFrom
                        val currenTo = state.currencyTo
                        val amountToCalc = state.amountToCalculate
                        Log.d("Inspection", "amountToCalculate = $amountToCalc," +
                                "currencFrom = $currencFrom; currenTo = $currenTo ")
                        findNavController().navigate(
                            MainScreenFragmentDirections
                                .actionMainScreenFragmentToCalculationScreenFragment(
                                    currencFrom,
                                    currenTo,
                                    amountToCalc
                                )
                        )
                    }
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}