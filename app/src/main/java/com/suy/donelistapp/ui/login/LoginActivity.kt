package com.suy.donelistapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.afollestad.vvalidator.form
import com.suy.donelistapp.R
import com.suy.donelistapp.base.BaseActivity
import com.suy.donelistapp.databinding.ActivityLoginBinding
import com.suy.donelistapp.ui.main.MainActivity
import com.suy.donelistapp.utils.Status
import com.suy.donelistapp.utils.string
import splitties.activities.start
import splitties.toast.longToast

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initiateViewModel()
        initiateForm()
    }

    private fun initiateViewModel() {
        viewModel.loginLiveData().observe(this, {
            when (it.status) {
                Status.LOADING -> showLoading
                Status.SUCCESS -> {
                    hideLoading
                    longToast(getString(R.string.text_welcome_user_value, it.data))
                    start<MainActivity> {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                }
                Status.ERROR -> {
                    hideLoading
                    longToast(it.message ?: 0)
                }
            }
        })
    }

    private fun initiateForm() {
        form {
            with(binding) {
                inputLayout(tilEmail) {
                    isEmail().description(R.string.text_email_invalid)
                    length().greaterThan(2).description(R.string.text_email_error)
                }
                inputLayout(tilPassword) {
                    length().greaterThan(7).description(R.string.text_password_error)
                }
                submitWith(btnLogin) {
                    viewModel.login(etEmail.string(), etPassword.string())
                }
            }
        }
    }
}