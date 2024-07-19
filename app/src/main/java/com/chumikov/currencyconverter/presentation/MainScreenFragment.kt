package com.chumikov.currencyconverter.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.chumikov.currencyconverter.R
import com.chumikov.currencyconverter.databinding.FragmentMainScreenBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


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

        val calcButton = binding.calculationButton

        calcButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.selectedItems.collect {
                    findNavController().navigate(
                        MainScreenFragmentDirections
                            .actionMainScreenFragmentToCalculationScreenFragment(
                                it.getValue(MainScreenViewModel.LEFT_SPINNER),
                                it.getValue(MainScreenViewModel.RIGHT_SPINNER)
                            )
                    )
                }
            }
        }

        val currencies = resources.getStringArray(R.array.currency_codes)
        val leftSpinner = binding.spinnerLeft
        val rightSpinner = binding.spinnerRight

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            currencies
        )
        leftSpinner.adapter = adapter
        rightSpinner.adapter = adapter

        leftSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setValue(MainScreenViewModel.LEFT_SPINNER to currencies[position])
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
                viewModel.setValue(MainScreenViewModel.RIGHT_SPINNER to currencies[position])
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}