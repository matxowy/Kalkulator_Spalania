package com.matxowy.kalkulatorspalania.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.matxowy.kalkulatorspalania.R
import com.matxowy.kalkulatorspalania.databinding.CalculatorFragmentBinding

class CalculatorFragment : Fragment() {

    private var _binding: CalculatorFragmentBinding? = null
    private val binding
        get() = _binding!!

    companion object {
        fun newInstance() = CalculatorFragment()
    }

    private lateinit var viewModel: CalculatorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CalculatorFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)


        binding.rgTypes.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_fuel_consumption -> {
                    makeVisibleFuelConsumptionView()
                }
                R.id.rb_costs -> {
                    makeVisibleCostsView()
                }
                else -> {
                    makeVisibleRangeView()
                }
            }
        }
    }

    private fun makeVisibleRangeView() {
        binding.llRangeView.visibility = View.VISIBLE

        binding.llFuelConsumptionView.visibility = View.GONE
        binding.llCostsView.visibility = View.GONE
    }

    private fun makeVisibleCostsView() {
        binding.llCostsView.visibility = View.VISIBLE

        binding.llFuelConsumptionView.visibility = View.GONE
        binding.llRangeView.visibility = View.GONE
    }

    private fun makeVisibleFuelConsumptionView() {
        binding.llFuelConsumptionView.visibility = View.VISIBLE

        binding.llCostsView.visibility = View.GONE
        binding.llRangeView.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}