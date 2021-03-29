package com.suy.donelistapp.ui.landingpage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.suy.donelistapp.databinding.ActivityLandingPageBinding
import com.suy.donelistapp.ui.login.LoginActivity
import com.suy.donelistapp.ui.register.RegisterActivity
import splitties.activities.start

class LandingPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initiateListener()
    }

    private fun initiateListener() {
        with(binding) {
            btnRegister.setOnClickListener { start<RegisterActivity>() }
            btnLogin.setOnClickListener { start<LoginActivity>() }
        }
    }
}