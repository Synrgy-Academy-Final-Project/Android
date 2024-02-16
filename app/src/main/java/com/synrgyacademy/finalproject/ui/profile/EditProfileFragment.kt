package com.synrgyacademy.finalproject.ui.profile

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.synrgyacademy.data.local.pref.SessionManager
import com.synrgyacademy.data.local.utils.dataStore
import com.synrgyacademy.domain.request.UserDetailRequest
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentEditProfileBinding
import com.synrgyacademy.finalproject.ui.passenger.UserDataState
import com.synrgyacademy.finalproject.utils.StringUtils.convertDepartureArrivalTime
import com.synrgyacademy.finalproject.utils.ViewUtils.setBottomMargin
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels<ProfileViewModel>()
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomMargin(binding.root)
        setSpinner()
        setData()
        onClick()
    }

    private fun onClick() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            saveButton.setOnClickListener {
                val names = nameEditText.text.toString().split(" ")
                val firstName: String
                val lastName: String

                if (names.size > 1) {
                    lastName = names.last()
                    firstName = names.dropLast(1).joinToString(" ")
                } else {
                    firstName = names[0]
                    lastName = names[0]
                }

                runBlocking {
                    requireContext().dataStore.data.map { it[SessionManager.KEY_TOKEN] }
                        .first()
                }?.let { it1 ->
                    viewModel.updateUser(
                        token = it1,
                        userData = UserDetailRequest(
                            firstName = firstName,
                            lastName = lastName,
                            dateOfBirth = bornDateEditText.text.toString(),
                            address = addressEditText.text.toString(),
                            gender = if (spinnerGender.selectedItem.toString() == "Laki-laki") "L" else "P",
                            phoneNumber = phoneEditText.text.toString(),
                            visa = visaEditText.text.toString(),
                            passport = passportEditText.text.toString(),
                            residentPermit = residentPermitEditText.text.toString(),
                            nationality = nationalityEditText.text.toString(),
                            nik = nikEditText.text.toString()
                        )
                    )
                }
            }

            bornDateInput.setEndIconOnClickListener {
                showDatePicker { date ->
                    bornDateEditText.setText(date)
                }
            }

            bornDateEditText.setOnClickListener {
                showDatePicker { date ->
                    bornDateEditText.setText(date)
                }
            }
        }
    }

    private fun setData() {
        viewModel.getUserData()
        viewModel.userData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is UserDataState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UserDataState.Error -> {
                    binding.progressBar.visibility = View.GONE
                }

                is UserDataState.Success -> {
                    viewModel.getUserDetailByToken(result.data.token)
                }
            }
        }

        viewModel.userDetailState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UserDetailState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UserDetailState.Error -> {
                    binding.progressBar.visibility = View.GONE
                }

                is UserDetailState.Success -> {
                    binding.apply {
                        progressBar.visibility = View.GONE
                        nameEditText.setText(
                            getString(
                                R.string.text_firstname_lastname,
                                state.data.userDetailDataModel?.firstName,
                                state.data.userDetailDataModel?.lastName
                            )
                        )
                        addressEditText.setText(state.data.userDetailDataModel?.address)
                        phoneEditText.setText(state.data.userDetailDataModel?.phoneNumber)
                        emailEditText.setText(state.data.email)
                        spinnerGender.setSelection(
                            if (state.data.userDetailDataModel?.gender == "L") 1 else if (state.data.userDetailDataModel?.gender == "P") 2 else 0
                        )
                        bornDateEditText.setText(if (state.data.userDetailDataModel?.dateOfBirth?.isNotEmpty() == true) state.data.userDetailDataModel?.dateOfBirth?.convertDepartureArrivalTime() else "")
                        nationalityEditText.setText(state.data.userDetailDataModel?.nationality)
                        nikEditText.setText(state.data.userDetailDataModel?.nik)
                        visaEditText.setText(state.data.userDetailDataModel?.visa)
                        passportEditText.setText(state.data.userDetailDataModel?.passport)
                        residentPermitEditText.setText(state.data.userDetailDataModel?.residentPermit)
                    }
                }
            }
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

                val sendFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val sendDate = sendFormat.format(selectedDate.time)

                onSelectedDate(sendDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun setSpinner() {
        // Reference the Spinner
        val spinner = binding.spinnerGender

        // Create an array of choices with the first item as the hint
        val choices =
            listOf("Pilih jenis kelamin") + resources.getStringArray(R.array.gender_array)
                .toList()

        // Create an ArrayAdapter using a default spinner layout and the array of choices
        val adapter = object : ArrayAdapter<String>(
            requireContext(),
            R.layout.spinner_gender,
            choices
        ) {
            override fun isEnabled(position: Int): Boolean {
                // Disable the first item from Spinner
                // First item will be used for hint
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView =
                    super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0) {
                    // Set the hint color to gray
                    view.setTextColor(Color.GRAY)
                } else {
                    view.setTextColor(Color.BLACK)
                }
                return view
            }
        }

        // Apply the adapter to the Spinner
        spinner.adapter = adapter

        // Handle the Spinner item selection
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // If the hint was selected, show it as a prompt and clear the selection
                if (position == 0) {
                    (parent.getChildAt(0) as TextView).text = null
                    (parent.getChildAt(0) as TextView).hint = "Pilih jenis kelamin"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}