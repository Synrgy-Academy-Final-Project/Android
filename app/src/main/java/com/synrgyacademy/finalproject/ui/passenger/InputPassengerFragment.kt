package com.synrgyacademy.finalproject.ui.passenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgyacademy.data.local.pref.SessionManager
import com.synrgyacademy.data.local.utils.dataStore
import com.synrgyacademy.domain.model.passenger.PassengerTotal
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.adapter.PassengerAdapter
import com.synrgyacademy.finalproject.databinding.FragmentInputPassengerBinding
import com.synrgyacademy.finalproject.ui.login.NotLoginDialogFragment
import com.synrgyacademy.finalproject.utils.ViewUtils.setBottomMargin
import com.synrgyacademy.finalproject.utils.parcelable
import com.synrgyacademy.finalproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class InputPassengerFragment : Fragment() {

    private var _binding: FragmentInputPassengerBinding? = null
    private val binding get() = _binding!!

    private val passengerAdapter: PassengerAdapter by lazy { PassengerAdapter() }

    private val viewModel: PassengerViewModel by viewModels<PassengerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =
            FragmentInputPassengerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        setBottomMargin(binding.root)
        onClick()
    }

    private fun setData() {
        val passengerTotal = arguments?.parcelable<PassengerTotal>("passengerTotal")
        binding.apply {
            rvPassenger.apply {
                adapter = passengerAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            passengerAdapter.submitList(passengerTotal!!)
        }

        passengerAdapter.onclick = {
            PassengerInputDialogFragment(
                it,
                (viewModel.userData.value as UserDataState.Success).data.token
            ).show(childFragmentManager, PassengerInputDialogFragment.TAG)
        }

        viewModel.getUserData()
        viewModel.userData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UserDataState.Loading -> binding.progressBar.visibility = View.VISIBLE

                is UserDataState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    requireContext().showToast(state.error)
                }

                is UserDataState.Success -> {
                    viewModel.getUserDataByToken(state.data.token)
                }
            }
        }

        viewModel.getUserDataByToken.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PassengerState.Loading -> binding.progressBar.visibility = View.VISIBLE

                is PassengerState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    if (state.error == "Kesalahan Server Internal") {
                        viewModel.expiredToken()
                        viewModel.deletedPassenger()
                    }
                }

                is PassengerState.Success -> {
                    binding.progressBar.visibility = View.GONE
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
    }

    private fun onClick() {
        binding.btnLanjut.setOnClickListener {
            viewModel.getAllPassenger()
            viewModel.allPassenger.value?.let {
                if (it is AllPassengerLocalState.Success) {
                    if (it.data.isEmpty()) {
                        requireContext().showToast("Tolong isi data penumpang terlebih dahulu")
                        return@setOnClickListener
                    }
                }
            }

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
                    findNavController().navigate(R.id.detailPassengerFragment, bundle)
                } else {
                    NotLoginDialogFragment().show(childFragmentManager, NotLoginDialogFragment.TAG)
                }
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
            viewModel.deletedPassenger()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
            viewModel.deletedPassenger()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}