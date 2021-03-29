package com.suy.donelistapp.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.suy.donelistapp.R
import com.suy.donelistapp.databinding.DialogLoadingBinding

class LoadingDialog(context: Context) : Dialog(context) {
    private lateinit var binding: DialogLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCancelable(false)
        window?.setBackgroundDrawableResource(R.color.transparent)
    }
}