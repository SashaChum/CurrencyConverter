package com.chumikov.currencyconverter.presentation

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.chumikov.currencyconverter.databinding.FragmentMainScreenBinding


class MainScreenFragment : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding: FragmentMainScreenBinding
        get() = _binding ?: throw RuntimeException("MainScreenFragment is null")

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
            findNavController().navigate(
                MainScreenFragmentDirections
                    .actionMainScreenFragmentToCalculationScreenFragment(
                        viewModel.mainScreenState.value.leftSpinnerValue,
                        viewModel.mainScreenState.value.rightSpinnerValue,
                        viewModel.mainScreenState.value.amountToCalculate
                    )
            )
        }

        val currencies = viewModel.mainScreenState.value.currencyList
        val leftSpinner = binding.spinnerLeft
        val rightSpinner = binding.spinnerRight

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            currencies
        )
        leftSpinner.adapter = adapter
        rightSpinner.adapter = adapter

        setFragmentResultListener("requestKey") { _, bundle ->
            val leftSpinnerVal = bundle.getString(CalculationScreenFragment.LEFT_SPINNER)
            val rightSpinnerVal = bundle.getString(CalculationScreenFragment.RIGHT_SPINNER)
            if (leftSpinnerVal != null && rightSpinnerVal != null ) {
                leftSpinner.setSelection(viewModel.mainScreenState.value.leftSpinnerIndex)
                rightSpinner.setSelection(viewModel.mainScreenState.value.rightSpinnerIndex)
            }
        }

        leftSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setLeftSpinnerState(currencies[position], position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        rightSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setRightSpinnerState(currencies[position], position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}