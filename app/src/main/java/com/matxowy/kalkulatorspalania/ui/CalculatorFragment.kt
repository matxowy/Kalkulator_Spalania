package com.matxowy.kalkulatorspalania.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.matxowy.kalkulatorspalania.R
import com.matxowy.kalkulatorspalania.databinding.CalculatorFragmentBinding
import com.matxowy.kalkulatorspalania.internal.SelectedTab
import kotlinx.coroutines.flow.collect

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

        addTextChangeListenersToConsumptionTab()
        addTextChangeListenersToCostsTab()
        addTextChangeListenersToRangeTab()

        addObservers()


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.calculatorEvent.collect { event ->
                when (event) {
                    is CalculatorViewModel.CalculatorEvent.ShowMessageAboutMissingData -> {
                        showMessageAboutMissingData()
                    }
                }
            }
        }
    }

    private fun showMessageAboutMissingData() {
        binding.apply {
            when {
                llCostsView.visibility == View.VISIBLE -> {
                    tvRequiredAmountOfFuel.text = "Uzupełnij wszystkie pola"
                    tvRequiredAmountOfFuel.visibility = View.VISIBLE
                }
                llFuelConsumptionView.visibility == View.VISIBLE -> {
                    tvAvgFuelConsumption.text = "Uzupełnij wymagane pola"
                    tvAvgFuelConsumption.visibility = View.VISIBLE
                }
                llRangeView.visibility == View.VISIBLE -> {
                    tvFilledWithFuel.text = "Uzupełnij wszystkie pola"
                    tvFilledWithFuel.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun addObservers() {
        viewModel.apply {
            currentRefueled.observe(viewLifecycleOwner, Observer {
                viewModel.doCalculation()
            })

            currentKmTraveled.observe(viewLifecycleOwner, Observer {
                viewModel.doCalculation()
            })

            currentFuelPrice.observe(viewLifecycleOwner, Observer {
                viewModel.doCalculation()
            })

            currentAvgFuelConsumption.observe(viewLifecycleOwner, Observer {
                viewModel.doCalculation()
            })

            currentNumberOfPeople.observe(viewLifecycleOwner, Observer {
                viewModel.doCalculation()
            })

            currentPaid.observe(viewLifecycleOwner, Observer {
                viewModel.doCalculation()
            })
        }
    }

    private fun addTextChangeListenersToRangeTab() {
        binding.apply {
            etAvgFuelConsumptionFromRangeTab.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.avgFuelConsumption = p0.toString().toDouble()
                    viewModel.currentAvgFuelConsumption.value = viewModel.avgFuelConsumption
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
            })

            etPaidFromRangeTab.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.paid = p0.toString().toDouble()
                    viewModel.currentPaid.value = viewModel.paid
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
            })

            etFuelPriceFromRangeTab.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.fuelPrice = p0.toString().toDouble()
                    viewModel.currentFuelPrice.value = viewModel.fuelPrice
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
            })
        }
    }

    private fun addTextChangeListenersToCostsTab() {
        binding.apply {
            etAvgFuelConsumptionFromCostsTab.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.avgFuelConsumption = p0.toString().toDouble()
                    viewModel.currentAvgFuelConsumption.value = viewModel.avgFuelConsumption
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
            })

            etKmTraveledFromCostsTab.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.kmTraveled = p0.toString().toDouble()
                    viewModel.currentKmTraveled.value = viewModel.kmTraveled
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
            })

            etFuelPriceFromCostsTab.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.fuelPrice = p0.toString().toDouble()
                    viewModel.currentFuelPrice.value = viewModel.fuelPrice
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
            })

            etNumberOfPeopleFromCostsTab.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.numberOfPeople = p0.toString().toInt()
                    viewModel.currentNumberOfPeople.value = viewModel.numberOfPeople
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
            })
        }
    }

    private fun addTextChangeListenersToConsumptionTab() {
        binding.apply {
            etRefueledFromFuelConsumptionTab.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.refueled = p0.toString().toDouble()
                    viewModel.currentRefueled.value = viewModel.refueled
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
            })

            etKmTraveledFromFuelConsumptionTab.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.kmTraveled = p0.toString().toDouble()
                    viewModel.currentKmTraveled.value = viewModel.kmTraveled
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
            })

            etFuelPriceFromFuelConsumptionTab.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.fuelPrice = p0.toString().toDouble()
                    viewModel.currentFuelPrice.value = viewModel.fuelPrice
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
            })
        }
    }

    private fun makeVisibleRangeView() {
        binding.apply {
            llRangeView.visibility = View.VISIBLE

            llFuelConsumptionView.visibility = View.GONE
            llCostsView.visibility = View.GONE
        }

        viewModel.currentTabSelected = SelectedTab.RANGE
    }

    private fun makeVisibleCostsView() {
        binding.apply {
            llCostsView.visibility = View.VISIBLE

            llFuelConsumptionView.visibility = View.GONE
            llRangeView.visibility = View.GONE
        }

        viewModel.currentTabSelected = SelectedTab.COSTS
    }

    private fun makeVisibleFuelConsumptionView() {
        binding.apply {
            llFuelConsumptionView.visibility = View.VISIBLE

            llRangeView.visibility = View.GONE
            llCostsView.visibility = View.GONE
        }

        viewModel.currentTabSelected = SelectedTab.CONSUMPTION
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}