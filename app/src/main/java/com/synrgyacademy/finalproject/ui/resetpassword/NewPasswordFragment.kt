package com.synrgyacademy.finalproject.ui.resetpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentNewPasswordBinding
import com.synrgyacademy.finalproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPasswordFragment : Fragment() {

    private var _binding: FragmentNewPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ResetPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewPasswordBinding.inflate(inflater, container, false)

        // Handle the back button event
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack(R.id.main_nav_graph, false)

            arguments?.getString("email").toString().let {
                val bundle = Bundle().apply {
                    putString("email", it)
                }
                findNavController().navigate(R.id.confirmationOtpFragment, bundle)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.changePasswordState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ChangePasswordState.Loading -> binding.progressBar.visibility = View.VISIBLE

                is ChangePasswordState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    requireContext().showToast(result.error)
                }

                is ChangePasswordState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    requireContext().showToast("Password berhasil diubah")
                    NewPasswordDialogFragment().show(childFragmentManager, NewPasswordDialogFragment.TAG)
                }
            }
        }

        binding.submitButton.setOnClickListener {
            if(isValidPassword(binding.newPasswordEditText.text.toString())){
                viewModel.changePassword(
                    arguments?.getString("email").toString(),
                    arguments?.getString("token").toString(),
                    binding.newPasswordEditText.text.toString(),
                    binding.repeatPasswordEditText.text.toString()
                )
            }
        }
    }

    private fun isValidPassword(password: String): Boolean {
        if (binding.newPasswordEditText.text.toString().isEmpty()) {
            binding.newPasswordEditText.error = getString(R.string.password_tidak_boleh_kosong)
            return false
        }

        if (binding.repeatPasswordEditText.text.toString().isEmpty()) {
            binding.repeatPasswordEditText.error = getString(R.string.password_tidak_boleh_kosong)
            return false
        }

        if (password.length < 8) {
            binding.newPasswordEditText.error = getString(R.string.error_password_length)
            return false
        }

        if (!password.any { it.isUpperCase() }) {
            binding.newPasswordEditText.error = getString(R.string.error_password_contain_upper_case)
            return false
        }

        if (!password.any { it.isLowerCase() }) {
            binding.newPasswordEditText.error = getString(R.string.error_password_contain_small_case)
            return false
        }

        if (!password.any { it.isDigit() }) {
            binding.newPasswordEditText.error = getString(R.string.error_password_contain_digit)
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
            binding.newPasswordEditText.error = getString(R.string.error_password_special_char)
            return false
        }

        if (binding.repeatPasswordEditText.text.toString() != binding.newPasswordEditText.text.toString()) {
            binding.repeatPasswordEditText.error = getString(R.string.error_password_not_same)
            return false
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}