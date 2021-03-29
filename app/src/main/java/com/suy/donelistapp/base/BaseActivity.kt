package com.suy.donelistapp.base

import androidx.appcompat.app.AppCompatActivity
import com.suy.donelistapp.dialog.LoadingDialog

abstract class BaseActivity : AppCompatActivity() {
    private val loading by lazy { LoadingDialog(this) }
    val showLoading by lazy { if (!loading.isShowing) loading.show() }
    val hideLoading by lazy { if (loading.isShowing) loading.dismiss() }
}