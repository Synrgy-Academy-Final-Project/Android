package com.synrgyacademy.finalproject.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.synrgyacademy.domain.model.UserData
import com.synrgyacademy.finalproject.MainActivity
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.ActivityLoginBinding
import com.synrgyacademy.finalproject.ui.RegisterActivity
import com.synrgyacademy.finalproject.ui.state.LoginState
import com.synrgyacademy.finalproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.sessionState.observe(this) { state ->
            when(state) {
                is LoginState.Loading -> {
                    binding.loginBtn.isEnabled = false
                }
                is LoginState.Error -> {
                    this.showToast(state.error)
                }
                is LoginState.Success -> {
                    viewModel.saveData(UserData(email = state.authData.email!!, name = state.authData.fullName!!, token = state.authData.token!!))
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    this.showToast(getString(R.string.login_berhasil))
                }
            }
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

        binding.registerNavigate.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
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
}