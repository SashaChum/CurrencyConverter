package com.chumikov.currencyconverter.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.chumikov.currencyconverter.R
import com.chumikov.currencyconverter.databinding.FragmentMainScreenBinding
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

        val editText = binding.editTextField
        editText.setText(viewModel.mainScreenState.value.amountToCalculate)

        val calcButton = binding.calculationButton
        calcButton.setOnClickListener {
            viewModel.setAmountState(editText.text.toString())
            val state = viewModel.mainScreenState.value
            findNavController().navigate(
                MainScreenFragmentDirections
                    .actionMainScreenFragmentToCalculationScreenFragment(
                        state.currencyFrom,
                        state.currencyTo,
                        state.amountToCalculate
                    )
            )
        }

        val spinnerCurrencyFrom = binding.spinnerCurrencyFrom
        val spinnerCurrencyTo = binding.spinnerCurrencyTo
        with(viewModel.mainScreenState.value) {
            spinnerCurrencyFrom.setText(currencyFrom)
            spinnerCurrencyTo.setText(currencyTo)
        }

        val adapterCurrencyFrom = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            viewModel.mainScreenState.value.currencyList
        )
        val adapterCurrencyTo = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            viewModel.mainScreenState.value.currencyList
        )

        spinnerCurrencyFrom.setAdapter(adapterCurrencyFrom)
        spinnerCurrencyTo.setAdapter(adapterCurrencyTo)

        spinnerCurrencyFrom.setOnItemClickListener { parent, _, position, _ ->
            val selectedValue = parent.getItemAtPosition(position).toString()
            Log.d("Inspection", "currencyFromSpinner selected Item: $selectedValue")
            viewModel.setCurrencyFromSpinnerState(selectedValue)
        }
        spinnerCurrencyTo.setOnItemClickListener { parent, _, position, _ ->
            val selectedValue = parent.getItemAtPosition(position).toString()
            Log.d("Inspection", "currencyToSpinner selected Item: $selectedValue")
            viewModel.setCurrencyToSpinnerState(selectedValue)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}