package com.synrgyacademy.finalproject.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentProfileBinding
import com.synrgyacademy.finalproject.utils.showAlert
import com.synrgyacademy.finalproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel:ProfileViewModel by viewModels<ProfileViewModel>()

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

        viewModel.isLoginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is IsLoginState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is IsLoginState.Error -> {
                    binding.progressBar.visibility = View.GONE

                    requireContext().showToast("Terjadi kesalahan, silahkan coba lagi")
                }

                is IsLoginState.Success -> {
                    binding.progressBar.visibility = View.GONE

                    if(state.isLogin) showView(true) else showView(false)
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

        binding.btnKeluar.setOnClickListener {
            requireContext().showAlert(
                title = getString(R.string.text_logout),
                message = getString(R.string.text_logout_dialog_title),
                positiveCallback = {
                    lifecycleScope.launch {
                        viewModel.logout()
                    }
                })
        }

        binding.btnMasuk.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
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