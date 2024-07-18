package com.chumikov.currencyconverter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.navArgs
import com.chumikov.currencyconverter.databinding.FragmentCalculationScreenBinding
import com.chumikov.currencyconverter.databinding.FragmentMainScreenBinding


class CalculationScreenFragment : Fragment() {

    private var _binding: FragmentCalculationScreenBinding? = null
    private val binding: FragmentCalculationScreenBinding
        get() = _binding ?: throw RuntimeException("CalculationScreenFragment is null")

    private val args by navArgs<CalculationScreenFragmentArgs>()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculationScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textView.text = "currencyFrom = ${args.fromCurrency}; currencyTo = ${args.toCurrency}"


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}