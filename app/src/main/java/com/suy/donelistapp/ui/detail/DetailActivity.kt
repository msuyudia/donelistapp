package com.suy.donelistapp.ui.detail

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import com.suy.donelistapp.R
import com.suy.donelistapp.base.BaseActivity
import com.suy.donelistapp.databinding.ActivityDetailBinding
import com.suy.donelistapp.room.entity.ActivityEntity
import com.suy.donelistapp.ui.main.MainActivity
import com.suy.donelistapp.utils.Status
import splitties.alertdialog.appcompat.negativeButton
import splitties.alertdialog.appcompat.onShow
import splitties.alertdialog.appcompat.positiveButton
import splitties.alertdialog.appcompat.titleResource
import splitties.alertdialog.material.materialAlertDialog
import splitties.toast.longToast

class DetailActivity : BaseActivity() {
    companion object {
        const val EXTRA_ID = "id"
    }

    private lateinit var binding: ActivityDetailBinding
    private val id by lazy { intent?.getIntExtra(EXTRA_ID, 0) ?: 0 }
    private var activityEntity: ActivityEntity? = null
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initiateViewModel()
        initiateListener()
    }

    private fun initiateViewModel() {
        viewModel.getDetailActivity(id)
        viewModel.detailLiveData().observe(this, {
            when (it.status) {
                Status.LOADING -> showLoading
                Status.SUCCESS -> {
                    activityEntity = it.data
                    hideLoading
                    with(binding) {
                        tvTitle.text = activityEntity?.title
                        tvDescription.text = activityEntity?.description
                    }
                }
                Status.ERROR -> {
                    hideLoading
                    longToast(it.message ?: 0)
                }
            }
        })
        viewModel.deleteLiveData().observe(this, {
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

    private fun initiateListener() {
        binding.btnDelete.setOnClickListener {
            materialAlertDialog {
                titleResource = R.string.title_alert_delete_activity
                setPositiveButton(R.string.text_yes) { _, _ ->
                    viewModel.deleteActivity(activityEntity)
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