package com.chumikov.currencyconverter.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.chumikov.currencyconverter.R
import com.chumikov.currencyconverter.databinding.FragmentCalculationScreenBinding
import kotlinx.coroutines.launch
import javax.inject.Inject


class CalculationScreenFragment : Fragment() {

    private val args by navArgs<CalculationScreenFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: CalculationScreenViewModel.Factory

    private val viewModel by getViewModel {
        viewModelFactory.get(
            args.fromCurrency,
            args.toCurrency,
            args.amount.toDouble()
        )
    }

    private val component by lazy {
        (requireActivity().application as MyApplication).component
    }

    private var _binding: FragmentCalculationScreenBinding? = null
    private val binding: FragmentCalculationScreenBinding
        get() = checkNotNull(_binding) { "CalculationScreenFragment is null" }


    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
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

        val resultTextView = binding.resultTextView
        val retryButton = binding.retryButton
        retryButton.setOnClickListener { viewModel.retry() }

        lifecycleScope.launch {
            viewModel.calculationScreenState.collect { state ->
                binding.loader.isVisible = state is CalculationScreenState.Loading
                retryButton.isVisible = state is CalculationScreenState.Error
                resultTextView.isVisible = state is CalculationScreenState.Content

                if (state is CalculationScreenState.Content) {
                    resultTextView.text = String.format(
                        getString(R.string.convertation_result),
                        args.fromCurrency,
                        args.toCurrency,
                        args.amount,
                        state.calcResult
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}