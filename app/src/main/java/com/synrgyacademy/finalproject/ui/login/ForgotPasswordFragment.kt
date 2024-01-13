package com.synrgyacademy.finalproject.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentForgotPasswordBinding
import com.synrgyacademy.finalproject.utils.AuthUtil.isValidEmail
import com.synrgyacademy.finalproject.utils.showAlert
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgotPasswordFragment : DialogFragment() {
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.forgotState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ForgetPasswordState.Loading -> binding.btnSubmit.isEnabled = false
                is ForgetPasswordState.Error -> {
                    binding.btnSubmit.isEnabled = true
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = result.error
                }

                is ForgetPasswordState.Success -> {
                    dismiss()
                    requireContext().showAlert(
                        "Lupa Password",
                        "Email telah di kirim, silah kan cek email anda",
                        positiveCallback = { it.dismiss() })
                }
            }
        }

        binding.btnSubmit.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            if (!isValidEmail(email)) {
                binding.etEmail.error = getString(R.string.error_email_empty)
            } else {
                viewModel.forgotPassword(email)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        const val TAG = "forgot password"
    }
}