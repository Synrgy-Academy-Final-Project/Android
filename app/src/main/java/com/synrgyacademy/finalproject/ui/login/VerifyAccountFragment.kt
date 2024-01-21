package com.synrgyacademy.finalproject.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentVerifyAccountBinding
import com.synrgyacademy.finalproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import `in`.aabhasjindal.otptextview.OTPListener

@AndroidEntryPoint
class VerifyAccountFragment : Fragment() {

    private var _binding: FragmentVerifyAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentVerifyAccountBinding.inflate(inflater, container, false)

        val navController = findNavController()
        val backStackEntry = navController.previousBackStackEntry
        val previousScreenId = backStackEntry?.destination?.id

        // Handle the back button event
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack(R.id.main_nav_graph, false)

            if (previousScreenId != null) {
                findNavController().navigate(previousScreenId)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.reqOtpSubtitle.text = getString(R.string.text_reset_password_subtitle, arguments?.getString("email"))

        viewModel.verifyAccountState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is VerifyAccountState.Loading -> binding.confirmButton.isEnabled = false

                is VerifyAccountState.Error -> {
                    requireContext().showToast(result.error)
                }

                is VerifyAccountState.Success -> {
                    requireContext().showToast("Anda berhasil verifikasi akun")
                    findNavController().popBackStack(R.id.main_nav_graph, false)
                    findNavController().navigate(R.id.ticket_navigation, null)
                }
            }
        }

        viewModel.forgotState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ForgetPasswordState.Loading -> binding.progressBar.visibility = View.VISIBLE

                is ForgetPasswordState.Error -> {
                    requireContext().showToast(result.error)
                }

                is ForgetPasswordState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    requireContext().showToast("Kode OTP berhasil dikirim ulang, silahkan cek email anda")
                }
            }
        }

        onClick()
    }

    private fun onClick() {
        binding.confirmButton.isEnabled = false
        binding.otpView.otpListener = object : OTPListener {
            override fun onInteractionListener() {
                // This method will be called on interaction with the OTP View
            }

            override fun onOTPComplete(otp: String) {
                // This method will be called when the OTP is completed
                binding.confirmButton.isEnabled = otp.isNotEmpty()
            }
        }

        binding.confirmButton.setOnClickListener {
            binding.otpView.otp?.let { otpCode ->
                if (otpCode.length == 6) {
                    val email = arguments?.getString("email")
                    if (email != null) {
                        viewModel.verifyAccount(email, otpCode)
                    }
                }
            }
        }

        binding.resendOtpButton.setOnClickListener {
            val email = arguments?.getString("email")
            if (email != null) {
                viewModel.forgotPassword(email)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}