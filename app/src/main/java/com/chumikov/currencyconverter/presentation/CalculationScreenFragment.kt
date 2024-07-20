package com.chumikov.currencyconverter.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chumikov.currencyconverter.R
import com.chumikov.currencyconverter.databinding.FragmentCalculationScreenBinding
import kotlinx.coroutines.launch


class CalculationScreenFragment : Fragment() {

    private var _binding: FragmentCalculationScreenBinding? = null
    private val binding: FragmentCalculationScreenBinding
        get() = _binding ?: throw RuntimeException("CalculationScreenFragment is null")

    private val args by navArgs<CalculationScreenFragmentArgs>()

    private val viewModel by viewModels<CalculationScreenViewModel> {
        CalcScreenVMFactoryTest(
            args.fromCurrency,
            args.toCurrency,
            args.amount.toDouble()
        )
    }





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


        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    setFragmentResult("requestKey",
                        bundleOf(
                            LEFT_SPINNER to args.fromCurrency,
                            RIGHT_SPINNER to args.toCurrency
                        )
                    )
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


        val resultTextView = binding.resultTextView


        lifecycleScope.launch {
            viewModel.mainScreenState.collect {state ->
                binding.retryButton.isInvisible = state !is CalculationScreenState.Error
                binding.loader.isInvisible = state !is CalculationScreenState.Loading
                resultTextView.isInvisible = state !is CalculationScreenState.Content

                if (state is CalculationScreenState.Content) {
                    resultTextView.text = String.format(
                        getString(R.string.convertation_result), state.calcResult
                    )
                }
            }
        }




//        binding.textView.text =
//            "currencyFrom = ${args.fromCurrency}; currencyTo = ${args.toCurrency}; Amount = ${args.amount}"


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        const val LEFT_SPINNER = "leftSpinner"
        const val RIGHT_SPINNER = "rightSpinner"
    }

}