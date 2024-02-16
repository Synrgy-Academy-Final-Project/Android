package com.synrgyacademy.finalproject.ui.ticket

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.synrgyacademy.domain.model.airport.AirportDataModel
import com.synrgyacademy.domain.model.passenger.PassengerTotal
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentNewFlightDialogBinding
import com.synrgyacademy.finalproject.utils.DateUtils
import com.synrgyacademy.finalproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class NewFlightDialogFragment : DialogFragment(), ClassDialogFragment.OnClassSelectedListener {

    private var _binding: FragmentNewFlightDialogBinding? = null
    private val binding get() = _binding!!
    private var isSwapped = false
    private val calendar = Calendar.getInstance()

    private val viewModel: AirportViewModel by activityViewModels()

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    interface OnScheduleSearchListener {
        fun onSchedule(
            departureData: AirportDataModel,
            arrivalData: AirportDataModel,
            date: String,
            classType: String,
            passengerTotal: PassengerTotal
        )
    }

    private var listenerData: OnScheduleSearchListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Set the listener to the parent fragment
        listenerData = parentFragment as? OnScheduleSearchListener
        if (listenerData == null) {
            throw ClassCastException("$parentFragment must implement OnClassSelectedListener")
        }
    }


    override fun onStart() {
        super.onStart()

        val lp: WindowManager.LayoutParams = WindowManager.LayoutParams()
        lp.copyFrom(requireDialog().window!!.attributes)
        // Subtract the desired margin size from the width of the screen
        val marginSize = resources.getDimensionPixelSize(R.dimen.dialog_horizontal_margin)
        val displayMetrics = resources.displayMetrics
        lp.width = displayMetrics.widthPixels - 2 * marginSize
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        requireDialog().window!!.attributes = lp
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewFlightDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        onClick()
    }

    private fun setData() {
        binding.apply {
            tvSelectedDate.text = DateUtils.getCurrentDateEEEDDMMM()
            tvSelectedDate.tag = DateUtils.getCurrentDDMMYYYY()
            tvPassengerTotal.text = getString(R.string.text_passenger_with_value, 1)
        }

        viewModel.passengerTotalState.observe(viewLifecycleOwner) { state ->
            binding.tvPassengerTotal.text = getString(
                R.string.text_passenger_with_value,
                state.adult + state.child + state.infant
            )
        }

        viewModel.arrivalDataState.observe(viewLifecycleOwner) { state ->
            binding.tvArrival.text = state.airportCodeName
        }

        viewModel.departureDataState.observe(viewLifecycleOwner) { state ->
            binding.tvDeparture.text = state.airportCodeName
        }
    }

    private fun onClick() {
        binding.apply {
            tvSelectedDate.setOnClickListener {
                showDatePicker { (displayDate, sendDate) ->
                    tvSelectedDate.text = displayDate
                    tvSelectedDate.tag = sendDate
                }
            }

            ivCalendar.setOnClickListener {
                showDatePicker { (displayDate, sendDate) ->
                    tvSelectedDate.text = displayDate
                    tvSelectedDate.tag = sendDate
                }
            }

            llPassenger.setOnClickListener {
                PassengerDialogFragment().show(childFragmentManager, PassengerDialogFragment.TAG)
            }

            llClass.setOnClickListener {
                ClassDialogFragment().show(childFragmentManager, ClassDialogFragment.TAG)
            }

            tvDeparture.setOnClickListener {
                val airportDialogFragment = AirportDialogFragment("Departure")
                airportDialogFragment.show(childFragmentManager, AirportDialogFragment.TAG)
            }

            ivPlaneDeparture.setOnClickListener {
                val airportDialogFragment = AirportDialogFragment("Departure")
                airportDialogFragment.show(childFragmentManager, AirportDialogFragment.TAG)
            }

            tvArrival.setOnClickListener {
                val airportDialogFragment = AirportDialogFragment("Arrival")
                airportDialogFragment.show(childFragmentManager, AirportDialogFragment.TAG)
            }

            ivPlaneArrival.setOnClickListener {
                val airportDialogFragment = AirportDialogFragment("Arrival")
                airportDialogFragment.show(childFragmentManager, AirportDialogFragment.TAG)
            }

            fabSwap.setOnClickListener {
                isSwapped = !isSwapped // toggle the value of isSwapped

                if (isSwapped) {
                    val temp = tvDeparture.text
                    tvDeparture.text = tvArrival.text
                    tvArrival.text = temp
                } else {
                    // swap them back to original
                    val temp = tvArrival.text
                    tvArrival.text = tvDeparture.text
                    tvDeparture.text = temp
                }
            }

            btnSearch.setOnClickListener {
                if (validateSearch()) {
                    // Call the interface method to pass the selected class data
                    val departureData =
                        if (isSwapped) viewModel.arrivalDataState.value!! else viewModel.departureDataState.value!!
                    val arrivalData =
                        if (isSwapped) viewModel.departureDataState.value!! else viewModel.arrivalDataState.value!!
                    if (viewModel.passengerTotalState.value == null) viewModel.setPassengerTotal(
                        1,
                        0,
                        0
                    ) else viewModel.passengerTotalState.value!!

                    listenerData?.onSchedule(
                        departureData,
                        arrivalData,
                        tvSelectedDate.tag.toString(),
                        tvClass.text.toString(),
                        viewModel.passengerTotalState.value!!
                    )
                    dismiss()
                } else {
                    requireContext().showToast("Tolong isi dan isi data dengan benar")
                }
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
        if (binding.tvSelectedDate.tag.toString() < DateUtils.getCurrentDDMMYYYY()) {
            requireContext().showToast("Tanggal keberangkatan tidak boleh kurang dari hari ini")
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

                val sendFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val sendDate = sendFormat.format(selectedDate.time)

                onSelectedDate(Pair(displayDate, sendDate))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "NewFlightDialogFragment"
    }

    override fun onClassSelected(selectedClass: String) {
        // Update the TextView with the selected class data
        binding.tvClass.text = selectedClass
    }
}