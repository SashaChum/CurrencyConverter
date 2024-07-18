package com.chumikov.currencyconverter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.chumikov.currencyconverter.databinding.FragmentMainScreenBinding


class MainScreenFragment : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding: FragmentMainScreenBinding
        get() = _binding ?: throw RuntimeException("MainScreenFragment is null")



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

        var leftSpinnerVal = "RUB"
        var rightSpinnerVal = "RUB"

        val calcButton = binding.calculationButton
        calcButton.setOnClickListener {
            findNavController().navigate(
                MainScreenFragmentDirections
                    .actionMainScreenFragmentToCalculationScreenFragment(leftSpinnerVal, rightSpinnerVal)
            )
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
                leftSpinnerVal = currencies[position]
                Log.d("SPINNERS", "leftSpinner: ${currencies[position]}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }

        }
        rightSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                rightSpinnerVal = currencies[position]
                Log.d("SPINNERS", "rightSpinner: ${currencies[position]}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}