package com.synrgyacademy.finalproject.ui.resetpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentConfirmationOtpBinding
import com.synrgyacademy.finalproject.ui.login.ForgetPasswordState
import com.synrgyacademy.finalproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import `in`.aabhasjindal.otptextview.OTPListener
import androidx.activity.addCallback
import com.synrgyacademy.finalproject.utils.StringUtils.censorEmail


@AndroidEntryPoint
class ConfirmationOtpFragment : Fragment() {

    private var _binding: FragmentConfirmationOtpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ResetPasswordViewModel by viewModels<ResetPasswordViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentConfirmationOtpBinding.inflate(inflater, container, false)

        // Handle the back button event
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack(R.id.main_nav_graph, false)

            findNavController().navigate(R.id.loginFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val censorEmail = arguments?.getString("email")?.censorEmail()
        binding.reqOtpSubtitle.text = getString(R.string.text_reset_password_subtitle, censorEmail)

        viewModel.resetState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResetPasswordState.Loading -> binding.progressBar.visibility = View.VISIBLE

                is ResetPasswordState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    requireContext().showToast(result.error)
                }

                is ResetPasswordState.Success -> {
                    binding.progressBar.visibility = View.GONE

                    requireContext().showToast("Kode OTP berhasil")
                    val email = arguments?.getString("email")
                    val kodeOTP = result.token
                    val bundle = Bundle().apply {
                        putString("email", email)
                        putString("token", kodeOTP)
                    }
                    findNavController().navigate(R.id.newPasswordFragment, bundle)
                }
            }
        }

        viewModel.resendOTPState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ForgetPasswordState.Loading -> binding.progressBar.visibility = View.VISIBLE
                is ForgetPasswordState.Error -> {
                    binding.progressBar.visibility = View.GONE
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
                if ((binding.otpView.otp?.length ?: 0) < 6) binding.confirmButton.isEnabled = false
            }

            override fun onOTPComplete(otp: String) {
                // This method will be called when the OTP is completed
                binding.confirmButton.isEnabled = otp.length == 6
            }
        }

        binding.confirmButton.setOnClickListener {
            binding.otpView.otp?.let { otpCode ->
                if (otpCode.length == 6) {
                    val email = arguments?.getString("email")
                    if (email != null) {
                        viewModel.verifiedOTP(email, otpCode)
                    }
                }
            }
        }

        binding.resendOtpButton.setOnClickListener {
            val email = arguments?.getString("email")
            if (email != null) {
                viewModel.resendOTP(email)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}