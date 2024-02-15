package com.synrgyacademy.finalproject.ui.ticket

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgyacademy.domain.model.airport.HistoryDataModel
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.adapter.HistorySearchAdapter
import com.synrgyacademy.finalproject.adapter.PopularPlacesAdapter
import com.synrgyacademy.finalproject.databinding.FragmentTicketBinding
import com.synrgyacademy.finalproject.ui.passenger.PassengerState
import com.synrgyacademy.finalproject.ui.passenger.UserDataState
import com.synrgyacademy.finalproject.utils.DateUtils.getCurrentDDMMYYYY
import com.synrgyacademy.finalproject.utils.DateUtils.getCurrentDateEEEDDMMM
import com.synrgyacademy.finalproject.utils.DateUtils.getCurrentYYYYMMDDHHMMSS
import com.synrgyacademy.finalproject.utils.showAlert
import com.synrgyacademy.finalproject.utils.showToast
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
    private val historyViewModel: HistoryViewModel by viewModels()

    private val popularPlacesAdapter: PopularPlacesAdapter by lazy { PopularPlacesAdapter() }
    private val historyAdapter: HistorySearchAdapter by lazy { HistorySearchAdapter() }

    private var isSwapped = false

    override fun onClassSelected(selectedClass: String) {
        // Update the TextView with the selected class data
        binding.tvClass.text = selectedClass
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        onClick()
    }

    private fun setData() {
        binding.apply {
            tvSelectedDate.text = getCurrentDateEEEDDMMM()
            tvSelectedDate.tag = getCurrentDDMMYYYY()
            tvPassengerTotal.text = getString(R.string.text_passenger_with_value, 1)

            rvPopularPlaces.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = popularPlacesAdapter
            }

            rvSearchHistory.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = historyAdapter
            }
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

        viewModel.getPopularPlaces()
        viewModel.popularPlace.observe(viewLifecycleOwner) { state ->
            popularPlacesAdapter.submitList(state)
        }

        historyViewModel.getAllHistory()
        historyViewModel.historyState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HistoryState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is HistoryState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    requireContext().showToast(state.error)
                }

                is HistoryState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val data = state.data
                    if (data.isNotEmpty()) {
                        historyAdapter.submitList(data)

                        binding.tvNoHistorySearching.visibility = View.GONE
                        binding.tvDeleteSearchHistory.visibility = View.VISIBLE
                    } else {
                        binding.tvNoHistorySearching.visibility = View.VISIBLE
                        binding.tvDeleteSearchHistory.visibility = View.GONE
                    }
                }
            }
        }

        historyAdapter.onclick = {
            val bundle = Bundle().apply {
                putParcelable("departureData", it.departureData)
                putParcelable("arrivalData", it.arrivalData)
                putString("date", getCurrentDDMMYYYY())
                putParcelable("passengerTotal", it.passenger)
                putString("class", it.airplaneClass)
            }

            findNavController().navigate(R.id.ticketListFragment, bundle)
        }

        historyViewModel.deleteHistoryState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DeleteHistoryState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is DeleteHistoryState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    requireContext().showToast(state.error)
                }

                is DeleteHistoryState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvDeleteSearchHistory.visibility = View.GONE
                    binding.tvNoHistorySearching.visibility = View.VISIBLE

                    historyAdapter.submitList(emptyList())
                    requireContext().showToast("Berhasil menghapus riwayat pencarian")
                }
            }
        }

        historyViewModel.getUserData()
        historyViewModel.userData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UserDataState.Loading -> {
                    // do nothing
                }

                is UserDataState.Error -> {
                    requireContext().showToast(state.error)
                }

                is UserDataState.Success -> {
                    historyViewModel.getUserDataByToken(state.data.token)
                }
            }
        }

        historyViewModel.getUserDataByToken.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PassengerState.Loading -> {
                    // do nothing
                }

                is PassengerState.Error -> {

                    if (state.error == "Kesalahan Server Internal") {
                        historyViewModel.expiredToken()
                        historyViewModel.deletedPassenger()
                    }

                }

                is PassengerState.Success -> {
                    // do nothing
                }
            }
        }
    }

    private fun onClick() {
        popularPlacesAdapter.onclick = {
            findNavController().navigate(R.id.detailPopularPlacesFragment, Bundle().apply {
                putParcelable("popularPlaces", it)
            })
        }

        binding.apply {
            ivMorePromo.setOnClickListener {
                findNavController().navigate(R.id.populer_places)
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

            tvArrival.setOnClickListener {
                AirportDialogFragment("Arrival").show(
                    childFragmentManager,
                    AirportDialogFragment.TAG
                )
            }

            tvDeparture.setOnClickListener {
                AirportDialogFragment("Departure").show(
                    childFragmentManager,
                    AirportDialogFragment.TAG
                )
            }

            ivCalendar.setOnClickListener {
                showDatePicker { (displayDate, sendDate) ->
                    tvSelectedDate.text = displayDate
                    tvSelectedDate.tag = sendDate
                }
            }

            ivCalendarBack.setOnClickListener {
                showDatePicker { (displayDate, sendDate) ->
                    tvSelectedDateBack.text = displayDate
                    tvSelectedDateBack.tag = sendDate
                }
            }

            mcPp.setOnCheckedChangeListener { _, isChecked ->
                llDateBack.visibility = if (isChecked) View.VISIBLE else View.GONE
            }

            llPassenger.setOnClickListener {
                PassengerDialogFragment().show(childFragmentManager, PassengerDialogFragment.TAG)
            }

            llClass.setOnClickListener {
                ClassDialogFragment().show(childFragmentManager, ClassDialogFragment.TAG)
            }

            tvDeleteSearchHistory.setOnClickListener {
                requireContext().showAlert(
                    "Hapus Riwayat Pencarian",
                    "Apakah anda yakin ingin menghapus semua riwayat pencarian?"
                ) {
                    historyViewModel.deleteAllHistory()
                }
            }

            btnSearch.setOnClickListener {
                if (validateSearch()) {
                    val bundle = Bundle().apply {
                        if(isSwapped) {
                            putParcelable("departureData", viewModel.arrivalDataState.value)
                            putParcelable("arrivalData", viewModel.departureDataState.value)
                        } else {
                            putParcelable("departureData", viewModel.departureDataState.value)
                            putParcelable("arrivalData", viewModel.arrivalDataState.value)
                        }

                        if (viewModel.passengerTotalState.value == null) viewModel.setPassengerTotal(
                            1,
                            0,
                            0
                        )
                        putParcelable("passengerTotal", viewModel.passengerTotalState.value)

                        putString("date", binding.tvSelectedDate.tag.toString())
                        putString("class", binding.tvClass.text.toString())
                    }

                    insertHistorySearching()
                    findNavController().navigate(R.id.ticketListFragment, bundle)
                }
            }
        }
    }

    private fun insertHistorySearching() {
        historyViewModel.insertHistory(
            HistoryDataModel(
                0,
                viewModel.departureDataState.value!!,
                viewModel.arrivalDataState.value!!,
                binding.tvSelectedDate.tag.toString(),
                getCurrentYYYYMMDDHHMMSS(),
                viewModel.passengerTotalState.value!!,
                binding.tvClass.text.toString()
            )
        )
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
        if (binding.tvSelectedDate.tag.toString() < getCurrentDDMMYYYY()) {
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