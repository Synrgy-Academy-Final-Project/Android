package com.synrgyacademy.finalproject.ui.ticket

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentTicketBinding
import com.synrgyacademy.finalproject.utils.DateUtils.getCurrentDateEEEDDMMM
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class TicketFragment : Fragment(), ClassDialogFragment.OnClassSelectedListener {
    private var _binding: FragmentTicketBinding? = null
    private val binding get() = _binding!!
    private val calendar = Calendar.getInstance()

    private val viewModel: AirportViewModel by activityViewModels()

    override fun onClassSelected(selectedClass: String) {
        // Update the TextView with the selected class data
        binding.tvClass.text = selectedClass
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSelectedDate.text = getCurrentDateEEEDDMMM()

        val currentDate = Calendar.getInstance().time
        val sendFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val sendDate = sendFormat.format(currentDate)
        binding.tvSelectedDate.tag = sendDate

        onClick()

        viewModel.arrivalDataState.observe(viewLifecycleOwner) { state ->
            binding.tvArrival.text = state.airportCodeName
        }

        viewModel.departureDataState.observe(viewLifecycleOwner) { state ->
            binding.tvDeparture.text = state.airportCodeName
        }
    }

    private fun onClick() {
        binding.tvArrival.setOnClickListener {
            AirportDialogFragment("Arrival").show(childFragmentManager, AirportDialogFragment.TAG)
        }

        binding.tvDeparture.setOnClickListener {
            AirportDialogFragment("Departure").show(childFragmentManager, AirportDialogFragment.TAG)
        }

        binding.ivCalendar.setOnClickListener {
            showDatePicker { (displayDate, sendDate) ->
                binding.tvSelectedDate.text = displayDate
                binding.tvSelectedDate.tag = sendDate
            }
        }

        binding.ivCalendarBack.setOnClickListener {
            showDatePicker { (displayDate, sendDate) ->
                binding.tvSelectedDateBack.text = displayDate
                binding.tvSelectedDateBack.tag = sendDate
            }
        }

        binding.mcPp.setOnCheckedChangeListener { _, isChecked ->
            binding.llDateBack.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        binding.llPassenger.setOnClickListener {
            PassengerDialogFragment().show(childFragmentManager, PassengerDialogFragment.TAG)
        }

        binding.llClass.setOnClickListener {
            ClassDialogFragment().show(childFragmentManager, ClassDialogFragment.TAG)
        }

        binding.btnSearch.setOnClickListener {
            if (validateSearch()) {
                val bundle = Bundle().apply {
                    putParcelable("departureData", viewModel.departureDataState.value)
                    putParcelable("arrivalData", viewModel.arrivalDataState.value)

                    if (viewModel.passengerTotalState.value == null) viewModel.setPassengerTotal(
                        1,
                        0,
                        0
                    )
                    putParcelable("passengerTotal", viewModel.passengerTotalState.value)

                    putString("date", binding.tvSelectedDate.tag.toString())
                    putString("class", binding.tvClass.text.toString())
                }
                findNavController().navigate(R.id.ticketListFragment, bundle)
            }
        }
    }

    private fun validateSearch(): Boolean {
        var error = 0
        if (binding.tvDeparture.text.isNullOrEmpty()) {
            binding.tvDeparture.error = "Silahkan pilih kota asal"
            error++
        }
        if (binding.tvArrival.text.isNullOrEmpty()) {
            binding.tvArrival.error = "Silahkan pilih kota tujuan"
            error++
        }
        if (binding.tvSelectedDate.text.isNullOrEmpty()) {
            binding.tvSelectedDate.error = "Silahkan pilih tanggal keberangkatan"
            error++
        }
        if (binding.tvClass.text == "Kelas") {
            binding.tvClass.error = "Silahkan pilih kelas"
            error++
        }
        return error == 0
    }

    private fun showDatePicker(
        onSelectedDate: (Pair<String, String>) -> Unit
    ) {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                val displayFormat = SimpleDateFormat("EEE, dd MMM", Locale("in", "ID"))
                val displayDate = displayFormat.format(selectedDate.time)

                val sendFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val sendDate = sendFormat.format(selectedDate.time)

                onSelectedDate(Pair(displayDate, sendDate))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}