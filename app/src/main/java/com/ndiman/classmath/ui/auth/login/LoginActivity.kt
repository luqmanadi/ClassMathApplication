package com.ndiman.classmath.ui.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ndiman.classmath.R
import com.ndiman.classmath.databinding.ActivityLoginBinding
import com.ndiman.classmath.ui.auth.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private var binding : ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        setUpAction()
        setAnimation()

    }

    private fun setUpAction(){
        binding?.registerButton?.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding?.usernameEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (s.length < 5){
                    binding?.usernameEditTextLayout?.error = resources.getString(R.string.error_input_username)
                } else{
                    binding?.usernameEditTextLayout?.error = null
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })

        binding?.passwordEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (s.toString().length < 8){
                    binding?.passwordEditTextLayout?.error = resources.getString(R.string.error_input_password)
                } else{
                    binding?.passwordEditTextLayout?.error = null
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun setAnimation(){
        ObjectAnimator.ofFloat(binding?.imageViewLogin, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding?.titleLogin, View.ALPHA, 1f).setDuration(300)
        val usernameInputText = ObjectAnimator.ofFloat(binding?.usernameEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val passwordInputText = ObjectAnimator.ofFloat(binding?.passwordEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val orText = ObjectAnimator.ofFloat(binding?.textOr, View.ALPHA, 1f).setDuration(300)
        val divider1 = ObjectAnimator.ofFloat(binding?.divider1, View.ALPHA, 1f).setDuration(300)
        val divider2 = ObjectAnimator.ofFloat(binding?.divider2, View.ALPHA, 1f).setDuration(300)
        val btnLogin = ObjectAnimator.ofFloat(binding?.loginButton, View.ALPHA, 1f).setDuration(300)
        val btnRegister = ObjectAnimator.ofFloat(binding?.registerButton, View.ALPHA, 1f).setDuration(300)

        val together = AnimatorSet().apply {
            playTogether(orText, divider1, divider2)
        }

        AnimatorSet().apply {
            playSequentially(title, usernameInputText, passwordInputText, btnLogin, together, btnRegister)
            start()
        }
    }

    private fun showLoading(state: Boolean) { binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}