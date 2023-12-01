package com.ndiman.classmath.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ndiman.classmath.databinding.ActivityOnBoardingBinding
import com.ndiman.classmath.ui.auth.login.LoginActivity

class OnBoardingActivity : AppCompatActivity() {

    private var binding : ActivityOnBoardingBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpAction()
    }

    private fun setUpAction(){
        binding?.btnGabung?.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}