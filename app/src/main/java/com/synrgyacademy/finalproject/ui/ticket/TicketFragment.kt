package com.synrgyacademy.finalproject.ui.ticket

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.synrgyacademy.finalproject.databinding.FragmentTicketBinding
import com.synrgyacademy.finalproject.utils.DateUtils.getCurrentDateEEEDDMMM
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class TicketFragment : Fragment() {
    private var _binding: FragmentTicketBinding? = null
    private val binding get() = _binding!!
    private val calendar = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSelectedDate.text = getCurrentDateEEEDDMMM()

        binding.tvArrival.setOnClickListener {
            AirportDialogFragment().show(childFragmentManager, AirportDialogFragment.TAG)
        }

        binding.tvDeparture.setOnClickListener {
            AirportDialogFragment().show(childFragmentManager, AirportDialogFragment.TAG)
        }

        binding.ivCalendar.setOnClickListener {
            showDatePicker(
                onSelectedDate = { binding.tvSelectedDate.text = it }
            )
        }

        binding.ivCalendarBack.setOnClickListener {
            showDatePicker(
                onSelectedDate = { binding.tvSelectedDateBack.text = it }
            )
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
    }

    private fun showDatePicker(
        onSelectedDate: (String) -> Unit
    ) {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                onSelectedDate(formattedDate)
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
    ): View? {
        _binding = FragmentTicketBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}