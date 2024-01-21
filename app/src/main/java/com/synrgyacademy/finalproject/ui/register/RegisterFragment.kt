package com.synrgyacademy.finalproject.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentRegisterBinding
import com.synrgyacademy.finalproject.utils.AuthUtil.isValidEmail
import com.synrgyacademy.finalproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel: RegisterViewModel by viewModels<RegisterViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerViewModel.registerState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is RegisterState.Loading -> binding.progressBar.visibility = View.VISIBLE

                is RegisterState.Error -> {
                    binding.progressBar.visibility = View.GONE

                    requireContext().showToast(result.error)
                }

                is RegisterState.Success -> {
                    binding.progressBar.visibility = View.GONE

                    requireContext().showToast(getString(R.string.register_success))

                    val bundle = Bundle().apply {
                        putString("email", binding.etEmail.text.toString())
                    }

                    findNavController().navigate(R.id.verifyAccountFragment, bundle)
                }
            }
        }

        binding.tvLogin.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnRegister.setOnClickListener {
            val fullName = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (isInputValid()) {
                registerViewModel.register(fullName, email, password)
            } else {
                requireContext().showToast(getString(R.string.error_empty_field))
            }
        }
    }

    private fun isInputValid(): Boolean {
        val fullName = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty() || !isValidEmail(email)) {
            binding.etEmail.error = getString(R.string.error_email_empty)
            return false
        }

        if (fullName.isEmpty()) {
            binding.etName.error = getString(R.string.error_name_empty)
            return false
        }

        if (phone.isEmpty()) {
            binding.etPhone.error = getString(R.string.error_phone_empty)
            return false
        }

        if (password.isEmpty()) {
            binding.etPassword.error = getString(R.string.error_password_empty)
            return false
        }
        return isValidPassword(password)

    }

    private fun isValidPassword(password: String): Boolean {
        if (password.length < 8) {
            binding.etPassword.error = getString(R.string.error_password_length)
            return false
        }

        if (!password.any { it.isUpperCase() }) {
            binding.etPassword.error = getString(R.string.error_password_contain_upper_case)
            return false
        }

        if (!password.any { it.isLowerCase() }) {
            binding.etPassword.error = getString(R.string.error_password_contain_small_case)
            return false
        }

        if (!password.any { it.isDigit() }) {
            binding.etPassword.error = getString(R.string.error_password_contain_digit)
            return false
        }

        val allowedCharacter = setOf(
            '(',
            '?',
            '=',
            '.',
            '*',
            '[',
            '@',
            '#',
            '\\',
            '$',
            '%',
            '^',
            '&',
            '+',
            '=',
            ':',
            '!',
            ']',
            ')',
            '\"',
            ']'
        )

        if (!password.any { allowedCharacter.contains(it) }) {
            binding.etPassword.error = getString(R.string.error_password_special_char)
            return false
        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)

        // Handle the back button event
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack(R.id.main_nav_graph, false)
            findNavController().navigate(R.id.loginFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}