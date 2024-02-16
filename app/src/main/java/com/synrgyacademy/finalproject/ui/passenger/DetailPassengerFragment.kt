package com.synrgyacademy.finalproject.ui.passenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgyacademy.data.local.pref.SessionManager
import com.synrgyacademy.data.local.utils.dataStore
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.adapter.PassengerDetailAdapter
import com.synrgyacademy.finalproject.databinding.FragmentDetailPassengerBinding
import com.synrgyacademy.finalproject.ui.login.NotLoginDialogFragment
import com.synrgyacademy.finalproject.utils.ViewUtils.setBottomMargin
import com.synrgyacademy.finalproject.utils.parcelable
import com.synrgyacademy.finalproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class DetailPassengerFragment : Fragment() {

    private var _binding: FragmentDetailPassengerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PassengerViewModel by viewModels<PassengerViewModel>()
    private val passengerDetailAdapter: PassengerDetailAdapter by lazy { PassengerDetailAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =
            FragmentDetailPassengerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        setBottomMargin(binding.root)
        onClick()
    }

    private fun setData() {

        viewModel.getUserData()
        viewModel.userData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UserDataState.Loading -> {
                    // do nothing
                }

                is UserDataState.Error -> {
                    requireContext().showToast(state.error)
                }

                is UserDataState.Success -> {
                    viewModel.getUserDataByToken(state.data.token)
                }
            }
        }

        viewModel.getUserDataByToken.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PassengerState.Loading -> {
                    // do nothing
                }

                is PassengerState.Error -> {
                    if (state.error == "Kesalahan Server Internal") {
                        viewModel.expiredToken()
                        viewModel.deletedPassenger()
                    }
                }

                is PassengerState.Success -> {
                    binding.apply {
                        textNameBookerValue.text = getString(
                            R.string.text_full_name_user,
                            state.data.userDetailDataModel?.firstName,
                            state.data.userDetailDataModel?.lastName
                        ) ?: ""
                        textPhoneBookerValue.text =
                            state.data.userDetailDataModel?.phoneNumber ?: ""
                        textEmailBookerValue.text =
                            (viewModel.userData.value as UserDataState.Success).data.email ?: ""
                    }
                }
            }
        }

        viewModel.getAllPassenger()
        binding.rvPassenger.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = passengerDetailAdapter
        }

        viewModel.allPassenger.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AllPassengerLocalState.Loading -> {
                    // do nothing
                }

                is AllPassengerLocalState.Error -> {
                    requireContext().showToast(state.error)
                }

                is AllPassengerLocalState.Success -> {
                    passengerDetailAdapter.submitList(state.data)
                }
            }
        }
    }

    private fun onClick() {
        binding.btnLanjut.setOnClickListener {
            runBlocking {
                val isLogin =
                    requireContext().dataStore.data.map { it[SessionManager.KEY_LOGIN] ?: false }
                        .first()
                if (isLogin) {
                    val bundle = Bundle().apply {
                        putParcelable("departureData", arguments?.parcelable("departureData"))
                        putParcelable("arrivalData", arguments?.parcelable("arrivalData"))
                        putString("flightDate", arguments?.getString("flightDate"))
                        putString("flightClass", arguments?.getString("flightClass"))
                        putParcelable("passengerTotal", arguments?.parcelable("passengerTotal"))
                        putParcelable("scheduleData", arguments?.parcelable("scheduleData"))
                    }
                    findNavController().navigate(R.id.detailTransactionFragment, bundle)
                } else {
                    NotLoginDialogFragment().show(childFragmentManager, NotLoginDialogFragment.TAG)
                }
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}