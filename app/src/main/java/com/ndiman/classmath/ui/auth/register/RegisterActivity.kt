package com.ndiman.classmath.ui.auth.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ndiman.classmath.R
import com.ndiman.classmath.data.Result
import com.ndiman.classmath.databinding.ActivityRegisterBinding
import com.ndiman.classmath.ui.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var binding : ActivityRegisterBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpAction()
        setAnimation()
    }

    private fun setUpAction(){
        binding?.topAppBar?.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        binding?.nameEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (s.toString().length < 3){
                    binding?.nameEditTextLayout?.error = resources.getString(R.string.error_input_name)
                }else{
                    binding?.nameEditTextLayout?.error = null
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })

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

        binding?.registerButton?.setOnClickListener {
            inputData()
        }
    }


    private fun inputData(){
        val name = binding?.nameEditText?.text.toString().trim()
        val username = binding?.usernameEditText?.text.toString().trim()
        val password = binding?.passwordEditText?.text.toString().trim()

        if (name.isEmpty() || username.isEmpty() || password.isEmpty()){
            showErrorDialog()
        } else{
            viewModel.userRegister(username, password, name).observe(this){result ->
                if (result != null){
                    when(result){
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            Toast.makeText(this, "Username $username berhasil mendaftar", Toast.LENGTH_SHORT).show()
                            showLoading(false)
                            MaterialAlertDialogBuilder(this)
                                .setTitle(resources.getString(R.string.success_register))
                                .setMessage(resources.getString(R.string.dialog_success_register,username))
                                .setPositiveButton(resources.getString(R.string.ok)){_, _ ->
                                    finish()
                                }
                                .create()
                                .show()
                        }
                        is Result.Error -> {
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                            showErrorDataIsAlreadyTaken()
                            showLoading(false)
                        }
                        else -> {}
                    }
                }
            }
        }

    }


    private fun showErrorDataIsAlreadyTaken(){
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.failed_register))
            .setMessage(resources.getString(R.string.username_is_already_taken))
            .setPositiveButton(resources.getString(R.string.ok)){_, _ ->
            }
            .create()
            .show()
    }

    private fun showErrorDialog(){
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.failed_register))
            .setMessage(resources.getString(R.string.dialog_failed_register))
            .setPositiveButton(resources.getString(R.string.ok)){_, _ ->
            }
            .create()
            .show()
    }


    private fun setAnimation(){
        ObjectAnimator.ofFloat(binding?.imageViewSingUp, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding?.titleSignUp, View.ALPHA, 1f).setDuration(300)
        val nameInputText = ObjectAnimator.ofFloat(binding?.nameEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val usernameInputText = ObjectAnimator.ofFloat(binding?.usernameEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val passwordInputText = ObjectAnimator.ofFloat(binding?.passwordEditTextLayout, View.ALPHA, 1f).setDuration(300)

        val btnRegister = ObjectAnimator.ofFloat(binding?.registerButton, View.ALPHA, 1f).setDuration(300)


        AnimatorSet().apply {
            playSequentially(title, nameInputText,usernameInputText, passwordInputText, btnRegister)
            start()
        }
    }

    private fun showLoading(state: Boolean) { binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}