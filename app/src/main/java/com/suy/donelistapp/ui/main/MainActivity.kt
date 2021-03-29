package com.suy.donelistapp.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import com.suy.donelistapp.R
import com.suy.donelistapp.base.BaseActivity
import com.suy.donelistapp.databinding.ActivityMainBinding
import com.suy.donelistapp.sharedpreference.PrefManager
import com.suy.donelistapp.ui.add.AddActivity
import com.suy.donelistapp.ui.detail.DetailActivity
import com.suy.donelistapp.ui.landingpage.LandingPageActivity
import com.suy.donelistapp.utils.Status
import splitties.activities.start
import splitties.alertdialog.appcompat.negativeButton
import splitties.alertdialog.appcompat.onShow
import splitties.alertdialog.appcompat.positiveButton
import splitties.alertdialog.appcompat.titleResource
import splitties.alertdialog.material.materialAlertDialog
import splitties.toast.longToast

class MainActivity : BaseActivity(), ItemSelectedListener {
    companion object {
        var isActivitiesChanged = false
    }

    private lateinit var binding: ActivityMainBinding
    private val user by lazy { PrefManager.getUserSession() }
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initiateViewModel()
        initiateUI()
        initiateListener()
    }

    override fun onResume() {
        super.onResume()
        if (isActivitiesChanged) viewModel.getAllActivity(user?.email ?: "")
    }

    private fun initiateViewModel() {
        viewModel.getAllActivity(user?.email ?: "")
        viewModel.activitiesLiveData().observe(this, {
            when (it.status) {
                Status.LOADING -> showLoading
                Status.SUCCESS -> {
                    hideLoading
                    binding.rvActivity.adapter =
                        ActivityAdapter(it.data ?: mutableListOf(), this@MainActivity)
                }
                Status.ERROR -> {
                    hideLoading
                    longToast(it.message ?: 0)
                }
            }
        })
    }

    private fun initiateUI() {
        binding.tvTitleName.text = getString(R.string.text_name_value, user?.name)
    }

    private fun initiateListener() {
        with(binding) {
            fabCreate.setOnClickListener {
                start<AddActivity>()
            }
            btnLogout.setOnClickListener {
                materialAlertDialog {
                    titleResource = R.string.title_alert_logout
                    setPositiveButton(R.string.text_yes) { _, _ ->
                        PrefManager.clearSession()
                        longToast(R.string.text_logout_success)
                        start<LandingPageActivity>()
                        this@MainActivity.finish()
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

    override fun onItemSelected(id: Int) {
        start<DetailActivity> { putExtra(DetailActivity.EXTRA_ID, id) }
    }
}