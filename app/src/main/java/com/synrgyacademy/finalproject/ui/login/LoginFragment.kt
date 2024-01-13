package com.synrgyacademy.finalproject.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.synrgyacademy.data.local.pref.SessionManager
import com.synrgyacademy.data.local.utils.dataStore
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentLoginBinding
import com.synrgyacademy.finalproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sessionState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is LoginState.Loading -> {
                    binding.loginBtn.isEnabled = false
                }
                is LoginState.Error -> {
                    requireContext().showToast(state.error)
                }
                is LoginState.Success -> {
                    val navOption = NavOptions.Builder()
                        .setPopUpTo(R.id.loginFragment, inclusive = true)
                        .build()
                    findNavController().navigate(R.id.ticket_navigation, null, navOption)
                    requireContext().showToast(getString(R.string.login_berhasil))
                }
            }
        }

        binding.forgotPassword.setOnClickListener {
            ForgotPasswordFragment().show(childFragmentManager, ForgotPasswordFragment.TAG)
        }

        onClick()
    }

    private fun onClick() {
        binding.loginBtn.setOnClickListener {
            if(validateLogin()) {
                lifecycleScope.launch {
                    viewModel.login(
                        binding.emailLoginEditText.text.toString(),
                        binding.passwordLoginEditText.text.toString()
                    )
                }
            }
        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
    }

    private fun validateLogin() : Boolean {
        var error = 0

        if(binding.emailLoginEditText.text.toString().isEmpty()) {
            binding.emailLoginEditText.error = getString(R.string.email_tidak_boleh_kosong)
            error++
        }

        if(binding.passwordLoginEditText.text.toString().isEmpty()) {
            binding.passwordLoginEditText.error = getString(R.string.password_tidak_boleh_kosong)
            error++
        }

        if(binding.passwordLoginEditText.text.toString().length < 8) {
            binding.passwordLoginEditText.error = getString(R.string.password_minimal_8_karakter)
            error++
        }

        return error == 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking {
            val isLogin =
                requireContext().dataStore.data.map { it[SessionManager.KEY_LOGIN] ?: false }
                    .first()
            Log.d("LoginFragment", "isLogin: $isLogin")
            if (isLogin) {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.loginFragment, true)
                    .build()
                findNavController().navigate(R.id.ticket_navigation, null, navOptions)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}