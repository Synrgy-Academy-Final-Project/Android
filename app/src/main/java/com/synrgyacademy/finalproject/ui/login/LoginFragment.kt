package com.synrgyacademy.finalproject.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.synrgyacademy.data.local.pref.SessionManager
import com.synrgyacademy.data.local.utils.dataStore
import com.synrgyacademy.finalproject.MainActivity
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentLoginBinding
import com.synrgyacademy.finalproject.ui.profile.NotificationState
import com.synrgyacademy.finalproject.utils.NotificationUtils.createHeadsUpNotification
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

    private val viewModel: LoginViewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sessionState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoginState.Loading -> binding.loginProgressBar.visibility = View.VISIBLE

                is LoginState.Error -> {
                    binding.loginProgressBar.visibility = View.GONE

                    if (state.error == "Dilarang: Akses ditolak") {
                        val bundle = Bundle().apply {
                            putString("email", binding.emailLoginEditText.text.toString())
                        }
                        viewModel.forgotPassword(binding.emailLoginEditText.text.toString())

                        findNavController().navigate(R.id.verifyAccountFragment, bundle)
                    } else {
                        requireContext().showToast(state.error)
                    }
                }

                is LoginState.Success -> {
                    binding.loginProgressBar.visibility = View.GONE
                    viewModel.getNotification()
                    viewModel.getNotification.observe(viewLifecycleOwner) { notificationState ->
                        if (notificationState is NotificationState.Success) {
                            if (notificationState.data) {
                                createHeadsUpNotification(
                                    requireContext(),
                                    "Kamu berhasil login",
                                    "Selamat datang di Fly.id",
                                    MainActivity::class.java
                                )
                            }
                            findNavController().popBackStack(R.id.main_nav_graph, false)
                            findNavController().navigate(R.id.ticket_navigation, null)
                            requireContext().showToast(getString(R.string.login_berhasil))
                        }
                    }
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
            if (validateLogin()) {
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

    private fun validateLogin(): Boolean {
        var error = 0

        if (binding.emailLoginEditText.text.toString().isEmpty()) {
            binding.emailLoginEditText.error = getString(R.string.email_tidak_boleh_kosong)
            error++
        }

        if (binding.passwordLoginEditText.text.toString().isEmpty()) {
            binding.passwordLoginEditText.error = getString(R.string.password_tidak_boleh_kosong)
            error++
        }

        if (binding.passwordLoginEditText.text.toString().length < 8) {
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
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}