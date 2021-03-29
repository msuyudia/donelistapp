package com.suy.donelistapp.ui.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.suy.donelistapp.databinding.ActivitySplashScreenBinding
import com.suy.donelistapp.sharedpreference.PrefManager
import com.suy.donelistapp.ui.landingpage.LandingPageActivity
import com.suy.donelistapp.ui.main.MainActivity
import splitties.activities.start

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                when (PrefManager.getUserSession()?.email.isNullOrEmpty()) {
                    true -> start<LandingPageActivity>()
                    false -> start<MainActivity>()
                }
                finish()
            }, 3000
        )
    }
}