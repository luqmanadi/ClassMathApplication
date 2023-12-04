package com.ndiman.classmath.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ndiman.classmath.databinding.ActivityOnBoardingBinding
import com.ndiman.classmath.ui.MainActivity
import com.ndiman.classmath.ui.ViewModelFactory
import com.ndiman.classmath.ui.auth.login.LoginActivity

class OnBoardingActivity : AppCompatActivity() {

    private val viewModel by viewModels<OnBoardingViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var binding : ActivityOnBoardingBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel.getSession().observe(this){user ->
            if (user.isLogin) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            } else{
                return@observe
            }
        }

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