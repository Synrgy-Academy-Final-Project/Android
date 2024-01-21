package com.synrgyacademy.finalproject.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentForgotPasswordBinding
import com.synrgyacademy.finalproject.utils.AuthUtil.isValidEmail
import com.synrgyacademy.finalproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgotPasswordFragment : DialogFragment() {
    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels<LoginViewModel>()

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
                    val email = binding.etEmail.text.toString().trim()
                    val bundle = Bundle().apply {
                        putString("email", email)
                    }
                    findNavController().navigate(R.id.confirmationOtpFragment, bundle)
                    requireContext().showToast(
                        getString(R.string.toast_otp_sent)
                    )
                    dismiss()
                }
            }
        }

        // Disable the button initially
        binding.btnSubmit.isEnabled = false

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // No action needed here
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // No action needed here
            }

            override fun afterTextChanged(s: Editable) {
                binding.btnSubmit.isEnabled = s.toString().trim().isNotEmpty()
            }
        })

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