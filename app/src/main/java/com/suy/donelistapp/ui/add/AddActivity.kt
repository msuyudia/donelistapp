package com.suy.donelistapp.ui.add

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import com.afollestad.vvalidator.form
import com.suy.donelistapp.R
import com.suy.donelistapp.base.BaseActivity
import com.suy.donelistapp.databinding.ActivityAddBinding
import com.suy.donelistapp.sharedpreference.PrefManager
import com.suy.donelistapp.ui.main.MainActivity
import com.suy.donelistapp.utils.Status
import com.suy.donelistapp.utils.string
import splitties.alertdialog.appcompat.negativeButton
import splitties.alertdialog.appcompat.onShow
import splitties.alertdialog.appcompat.positiveButton
import splitties.alertdialog.appcompat.titleResource
import splitties.alertdialog.material.materialAlertDialog
import splitties.toast.longToast

class AddActivity : BaseActivity() {
    private lateinit var binding: ActivityAddBinding
    private val user by lazy { PrefManager.getUserSession() }
    private val viewModel by viewModels<AddViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initiateViewModel()
        initiateUI()
        initiateForm()
    }

    private fun initiateViewModel() {
        viewModel.addedLiveData().observe(this, {
            when (it.status) {
                Status.LOADING -> showLoading
                Status.SUCCESS -> {
                    hideLoading
                    longToast(it.data ?: 0)
                    MainActivity.isActivitiesChanged = true
                    onBackPressed()
                }
                Status.ERROR -> {
                    hideLoading
                    longToast(it.message ?: 0)
                }
            }
        })
    }

    private fun initiateUI() {
        binding.tvAsk.text = getString(R.string.text_ask_value, user?.name)
    }

    private fun initiateForm() {
        form {
            with(binding) {
                input(etTitle) {
                    length().greaterThan(0).description(R.string.text_title_error)
                }
                input(etDescription) {
                    length().greaterThan(0).description(R.string.text_desc_error)
                }
                submitWith(btnAdd) {
                    materialAlertDialog {
                        titleResource = R.string.title_alert_add_activity
                        setPositiveButton(R.string.text_yes) { _, _ ->
                            viewModel.saveActivity(
                                user?.email,
                                etTitle.string(),
                                etDescription.string()
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