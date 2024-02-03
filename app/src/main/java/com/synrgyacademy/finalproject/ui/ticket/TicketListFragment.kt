package com.synrgyacademy.finalproject.ui.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgyacademy.domain.model.airport.AirportDataModel
import com.synrgyacademy.domain.model.airport.PassengerTotal
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.adapter.DateTicketAdapter
import com.synrgyacademy.finalproject.adapter.TicketAdapter
import com.synrgyacademy.finalproject.databinding.FragmentTicketListBinding
import com.synrgyacademy.finalproject.utils.parcelable
import com.synrgyacademy.finalproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketListFragment : Fragment() {

    private var _binding: FragmentTicketListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AirportViewModel by viewModels<AirportViewModel>()
    private val dateTicketAdapter: DateTicketAdapter by lazy { DateTicketAdapter() }
    private val ticketAdapter: TicketAdapter by lazy { TicketAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTicketListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        setRecyclerView()
        setClick()
    }

    private fun setClick() {
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.filterButton.setOnClickListener {

        }
    }

    private fun setRecyclerView() {
        binding.departureSchedule.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = dateTicketAdapter
        }

        binding.departureList.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = ticketAdapter
        }

    }

    private fun setData() {
        val getDeparture = arguments?.parcelable("departureData") as? AirportDataModel
        val getArrival = arguments?.parcelable("arrivalData") as? AirportDataModel
        val getDateFlight = arguments?.getString("date")
        val getClass = arguments?.getString("class")
        val getPassenger = arguments?.parcelable("passengerTotal") as? PassengerTotal

        binding.flightsFrom.text = getDeparture?.airportCityCode
        binding.flightsTo.text = getArrival?.airportCityCode
        binding.flightClass.text = getClass

        val totalPassenger =
            (getPassenger?.adult ?: 0) + (getPassenger?.child ?: 0) + (getPassenger?.infant ?: 0)
        binding.flightPersonCount.text = getString(R.string.passenger_total, totalPassenger)

        if (getDeparture != null && getArrival != null && getDateFlight != null && getClass != null) {
            viewModel.getScheduleFlight(
                getDeparture.airportCode,
                getArrival.airportCode,
                getDateFlight,
                getClass
            )
            viewModel.getMinimumPrice(
                getDeparture.airportCode,
                getArrival.airportCode,
                getDateFlight
            )
        }

        viewModel.scheduleState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScheduleState.Loading -> binding.progressBar.visibility = View.VISIBLE

                is ScheduleState.Error -> {
                    binding.progressBar.visibility = View.GONE
                }

                is ScheduleState.Success -> {
                    binding.progressBar.visibility = View.GONE

                    ticketAdapter.submitList(state.data)
                }
            }
        }

        viewModel.minimumPrice.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MinimumPriceState.Loading -> binding.progressBar.visibility = View.VISIBLE

                is MinimumPriceState.Error -> {
                    requireContext().showToast(state.error)
                }

                is MinimumPriceState.Success -> {
                    dateTicketAdapter.submitList(state.data)
                }
            }
        }

        dateTicketAdapter.onclick = {
            viewModel.getScheduleFlight(
                getDeparture?.airportCode ?: "",
                getArrival?.airportCode ?: "",
                it.date,
                getClass ?: ""
            )
            Toast.makeText(requireContext(), it.date, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}