package com.suy.donelistapp.ui.register

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import com.afollestad.vvalidator.form
import com.suy.donelistapp.R
import com.suy.donelistapp.base.BaseActivity
import com.suy.donelistapp.databinding.ActivityRegisterBinding
import com.suy.donelistapp.ui.main.MainActivity
import com.suy.donelistapp.utils.Status
import com.suy.donelistapp.utils.string
import splitties.activities.start
import splitties.alertdialog.appcompat.*
import splitties.alertdialog.material.materialAlertDialog
import splitties.toast.longToast

class RegisterActivity : BaseActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initiateViewModel()
        initiateForm()
    }

    private fun initiateViewModel() {
        viewModel.registerLiveData().observe(this, {
            when (it.status) {
                Status.LOADING -> showLoading
                Status.SUCCESS -> {
                    hideLoading
                    longToast(it.data ?: 0)
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
                inputLayout(tilName) {
                    length().greaterThan(2).description(R.string.text_name_error)
                }
                inputLayout(tilEmail) {
                    isEmail().description(R.string.text_email_invalid)
                    length().greaterThan(2).description(R.string.text_email_error)
                }
                inputLayout(tilPassword) {
                    length().greaterThan(7).description(R.string.text_password_error)
                }
                submitWith(btnRegister) {
                    val name = etName.string()
                    val email = etEmail.string()
                    val password = etPassword.string()
                    materialAlertDialog {
                        titleResource = R.string.title_alert_register
                        message =
                            getString(R.string.text_message_register_value, name, email, password)
                        setPositiveButton(R.string.text_yes) { dialog, _ ->
                            viewModel.register(
                                name,
                                email,
                                password
                            )
                        }
                        setNegativeButton(R.string.text_no) { dialog, _ ->
                            dialog.dismiss()
                        }
                    }.onShow {
                        positiveButton.setTextColor(Color.GREEN)
                        negativeButton.setTextColor(Color.RED)
                        positiveButton.isAllCaps = false
                        negativeButton.isAllCaps = false
                    }.show()
                }
            }
        }
    }
}