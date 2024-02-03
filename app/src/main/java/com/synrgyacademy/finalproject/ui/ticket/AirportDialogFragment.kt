package com.synrgyacademy.finalproject.ui.ticket

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.adapter.AirportAdapter
import com.synrgyacademy.finalproject.databinding.FragmentAirportDialogBinding
import com.synrgyacademy.finalproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AirportDialogFragment(private val sourceClick: String) : DialogFragment() {
    private var _binding: FragmentAirportDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AirportViewModel by activityViewModels()
    private val airportAdapter: AirportAdapter by lazy { AirportAdapter() }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAirportDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAirport()

        binding.rvAirport.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = airportAdapter
        }

        binding.tvCancel.setOnClickListener {
            dismiss()
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // No action needed here
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed here
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.getAirport(s.toString())
            }
        })

        viewModel.airportState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AirportState.Loading -> binding.progressBar.visibility = View.VISIBLE
                is AirportState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    requireContext().showToast(state.error)
                }

                is AirportState.Success -> {
                    binding.progressBar.visibility = View.GONE

                    airportAdapter.submitList(state.data)
                    Log.d("DATA", "onViewCreated: ${state.data}")
                }
            }
        }

        airportAdapter.onclick = { airport ->
            when (sourceClick) {
                "Arrival" -> {
                    viewModel.setArrivalData(airport)
                }

                "Departure" -> {
                    viewModel.setDepartureData(airport)
                }

                else -> {
                    Log.d(
                        "TAG",
                        "onViewCreatedError: ${airport.airportCityCode} ${airport.airportCode}"
                    )
                }
            }
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "airport dialog"
    }

}