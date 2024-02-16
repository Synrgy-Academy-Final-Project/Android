package com.synrgyacademy.finalproject.ui.passenger

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.synrgyacademy.domain.model.passenger.PassengerDataModel
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentPassengerInputDialogBinding
import com.synrgyacademy.finalproject.utils.StringUtils.yyyyMMddTHHmmssZtoDDMMMYYYY
import com.synrgyacademy.finalproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class PassengerInputDialogFragment(private val passengerType: String, private val token: String) :
    DialogFragment() {

    private var _binding: FragmentPassengerInputDialogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PassengerViewModel by viewModels<PassengerViewModel>()
    private val calendar = Calendar.getInstance()
    private var userId = 0

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onStart() {
        super.onStart()
        requireDialog().window?.apply {
            setLayout(WRAP_CONTENT, WRAP_CONTENT)
        }

        view?.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            setMargins(16, 16, 16, 16)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =
            FragmentPassengerInputDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        onClick()
    }

    private fun setData() {
        binding.tvPassengerTitle.text = passengerType

        if (passengerType.contains("Anak") || passengerType.contains("Bayi")) {
            binding.cbSameAsPassenger.visibility = View.GONE
            binding.phoneTitle.visibility = View.GONE
            binding.phoneInput.visibility = View.GONE
            binding.bornDateTitle.text = when {
                passengerType.contains("Anak") -> getString(R.string.text_child_two_until_eleven)
                passengerType.contains("Bayi") -> getString(R.string.text_baby_under_two)
                else -> ""
            }
        }

        viewModel.getPassengerByType(passengerType)
        viewModel.getUserByType.observe(viewLifecycleOwner) { result ->
            when (result) {
                is PassengerLocalState.Loading -> {
                    // do nothing
                }

                is PassengerLocalState.Error -> {
                    // do nothing
                }

                is PassengerLocalState.Success -> {
                    userId = result.data.id
                    if (result.data.gender == "L") binding.rbMen.isChecked =
                        true else if (result.data.gender == "P") binding.rbWoman.isChecked = true
                    binding.nameEditText.setText(result.data.name)
                    binding.dateEditText.setText(result.data.bornDate)
                    binding.phoneEditText.setText(result.data.phoneNumber)
                }
            }
        }

        viewModel.getUserDataByToken.observe(viewLifecycleOwner) { result ->
            when (result) {
                is PassengerState.Loading -> {
                    // do nothing
                }

                is PassengerState.Error -> {
                    requireContext().showToast(result.error)
                }

                is PassengerState.Success -> {
                    binding.nameEditText.setText(
                        getString(
                            R.string.text_full_name_user,
                            result.data.userDetailDataModel?.firstName,
                            result.data.userDetailDataModel?.lastName
                        )
                    )

                    if(result.data.userDetailDataModel?.dateOfBirth?.isNotEmpty() == true) binding.dateEditText.setText(result.data.userDetailDataModel?.dateOfBirth?.yyyyMMddTHHmmssZtoDDMMMYYYY()?: "")
                    binding.phoneEditText.setText(
                        result.data.userDetailDataModel?.phoneNumber ?: ""
                    )
                    if (result.data.userDetailDataModel?.gender == "L") {
                        binding.rbMen.isChecked = true
                    } else if (result.data.userDetailDataModel?.gender == "P") {
                        binding.rbWoman.isChecked = true
                    }
                }
            }
        }
    }

    private fun onClick() {
        binding.cbSameAsPassenger.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.getUserDataByToken(token)
            } else {
                binding.nameEditText.setText("")
                binding.dateEditText.setText("")
                binding.phoneEditText.setText("")
            }
        }

        binding.btnSave.setOnClickListener {
            if (!validateValue()) {
                requireContext().showToast(getString(R.string.error_empty_field))
                return@setOnClickListener
            }

            val passengerDataModel = createPassengerDataModel(userId)
            when (userId) {
                0 -> viewModel.insertPassenger(passengerDataModel)
                else -> viewModel.updatePassenger(passengerDataModel)
            }

            dismiss()
        }

        binding.dateInput.setOnClickListener {
            showDatePicker {
                binding.dateEditText.setText(it)
            }
        }

        binding.dateEditText.setOnClickListener {
            showDatePicker {
                binding.dateEditText.setText(it)
            }
        }
    }

    private fun createPassengerDataModel(id: Int): PassengerDataModel {
        return PassengerDataModel(
            id,
            binding.nameEditText.text.toString(),
            binding.dateEditText.text.toString(),
            if (binding.rbMen.isChecked) "L" else "P",
            binding.phoneEditText.text.toString(),
            passengerType
        )
    }

    private fun showDatePicker(
        onSelectedDate: (String) -> Unit
    ) {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                val displayFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val displayDate = displayFormat.format(selectedDate.time)

                onSelectedDate(displayDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun validateValue(): Boolean {
        var error = 0

        if (binding.nameEditText.text.toString().isEmpty()) {
            binding.nameEditText.error = getString(R.string.error_name_empty)
            error++
        }

        if (binding.dateEditText.text.toString().isEmpty()) {
            binding.dateEditText.error = getString(R.string.error_date_empty)
            error++
        }

        if (binding.phoneInput.visibility == View.VISIBLE && binding.phoneEditText.text.toString()
                .isEmpty()
        ) {
            binding.phoneEditText.error = getString(R.string.error_phone_empty)
            error++
        }

        if (binding.rbMen.isChecked.not() && binding.rbWoman.isChecked.not()) {
            requireContext().showToast(getString(R.string.error_check_empty))
            error++
        }

        return error == 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "PassengerInputDialogFragment"
    }
}