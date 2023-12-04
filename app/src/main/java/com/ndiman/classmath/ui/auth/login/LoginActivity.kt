package com.ndiman.classmath.ui.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ndiman.classmath.R
import com.ndiman.classmath.data.Result
import com.ndiman.classmath.data.pref.UserModel
import com.ndiman.classmath.databinding.ActivityLoginBinding
import com.ndiman.classmath.ui.MainActivity
import com.ndiman.classmath.ui.ViewModelFactory
import com.ndiman.classmath.ui.auth.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

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

        binding?.loginButton?.setOnClickListener {
            inputData()
        }
    }

    private fun inputData(){
        val username = binding?.usernameEditText?.text.toString()
        val password = binding?.passwordEditText?.text.toString()

        if (username.isEmpty() || password.isEmpty()){
            showErrorDialog()
        } else {
            viewModel.loginUser(username, password).observe(this){result ->
                if (result != null){
                    when(result){
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            viewModel.saveSession(
                                UserModel(
                                    token = result.data.data.token,
                                    isLogin = true)
                            )
                            Log.d("Success", "Cek Token : ${result.data.data.token}")
                            showLoading(false)
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                            MaterialAlertDialogBuilder(this)
                                .setTitle(resources.getString(R.string.success_login))
                                .setMessage(resources.getString(R.string.dialog_success_login))
                                .setPositiveButton(resources.getString(R.string.ok)){_, _ ->
                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                .create()
                                .show()
                        }
                        is Result.Error -> {
                            showLoading(false)
                            showErrorDialog()
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun showErrorDialog(){
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.failed_login))
            .setMessage(resources.getString(R.string.dialog_failed_login))
            .setPositiveButton(resources.getString(R.string.ok)){_, _ ->
            }
            .create()
            .show()
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