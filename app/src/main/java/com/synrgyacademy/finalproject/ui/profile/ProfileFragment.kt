package com.synrgyacademy.finalproject.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentProfileBinding
import com.synrgyacademy.finalproject.ui.passenger.PassengerViewModel
import com.synrgyacademy.finalproject.ui.passenger.UserDataState
import com.synrgyacademy.finalproject.utils.showAlert
import com.synrgyacademy.finalproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels<ProfileViewModel>()
    private val passengerViewModel: PassengerViewModel by viewModels<PassengerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        onClick()
    }

    private fun setData() {
        viewModel.isLoginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is IsLoginState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is IsLoginState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    requireContext().showToast(state.error)
                }

                is IsLoginState.Success -> {
                    if (state.isLogin) {
                        viewModel.getUserData()
                        showView(true)
                    } else {
                        binding.progressBar.visibility = View.GONE
                        showView(false)
                    }
                }
            }
        }

        viewModel.userData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UserDataState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UserDataState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    requireContext().showToast(state.error)
                }

                is UserDataState.Success -> {
                    viewModel.getUserDetailByToken(state.data.token)
                }
            }
        }

        viewModel.userDetailState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UserDetailState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UserDetailState.Error -> {
                    if (state.error == "Kesalahan Server Internal") {
                        viewModel.logout()
                        viewModel.deleteAllPassenger()
                        showView(false)
                    }

                    binding.progressBar.visibility = View.GONE
                    requireContext().showToast(state.error)
                }

                is UserDetailState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvName.text = getString(R.string.text_firstname_lastname, state.data.userDetailDataModel?.firstName, state.data.userDetailDataModel?.lastName)
                    binding.tvEmail.text = state.data.email
                }
            }
        }

        viewModel.logoutState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AuthState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is AuthState.Error -> {
                    requireContext().showToast("Logout tidak berhasil, silahkan coba lagi")

                    binding.progressBar.visibility = View.GONE
                }

                is AuthState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    showView(false)

                    findNavController().popBackStack(R.id.main_nav_graph, false)
                    findNavController().navigate(R.id.profile_navigation)
                }
            }
        }
    }

    private fun onClick() {
        binding.apply {
            btnKeluar.setOnClickListener {
                requireContext().showAlert(
                    title = getString(R.string.text_logout),
                    message = getString(R.string.text_logout_dialog_title),
                    positiveCallback = {
                        lifecycleScope.launch {
                            viewModel.logout()
                            passengerViewModel.deletedPassenger()
                        }
                    })
            }

            ivBell.setOnClickListener {
                findNavController().navigate(R.id.notificationFragment)
            }

            ivSetting.setOnClickListener {
                findNavController().navigate(R.id.helpCentreFragment)
            }

            btnMasuk.setOnClickListener {
                findNavController().navigate(R.id.loginFragment)
            }

            btnEditProfil.setOnClickListener {
                findNavController().navigate(R.id.editProfileFragment)
            }

            clPusatBantuan.setOnClickListener {
                findNavController().navigate(R.id.helpCentreFragment)
            }

            clHubungiKami.setOnClickListener {
                findNavController().navigate(R.id.contactUsFragment)
            }

            clNotifikasi.setOnClickListener {
                findNavController().navigate(R.id.notificationFragment)
            }
        }
    }

    private fun showView(isLogin: Boolean) {
        binding.cvNotLogin.visibility = if (isLogin) View.GONE else View.VISIBLE

        binding.llTicketConfiguration.visibility = if (isLogin) View.VISIBLE else View.GONE
        binding.llUserHelp.visibility = if (isLogin) View.VISIBLE else View.GONE

        binding.tvAnggotaFly.visibility = if (isLogin) View.VISIBLE else View.GONE
        binding.btnEditProfil.visibility = if (isLogin) View.VISIBLE else View.GONE
        binding.tvName.visibility = if (isLogin) View.VISIBLE else View.GONE
        binding.tvEmail.visibility = if (isLogin) View.VISIBLE else View.GONE

        binding.btnKeluar.visibility = if (isLogin) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}