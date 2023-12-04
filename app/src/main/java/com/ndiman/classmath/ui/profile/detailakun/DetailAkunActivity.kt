package com.ndiman.classmath.ui.profile.detailakun

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
import com.ndiman.classmath.databinding.ActivityDetailAkunBinding
import com.ndiman.classmath.ui.ViewModelFactory

class DetailAkunActivity : AppCompatActivity() {

    private val viewModel by viewModels<DetailAkunViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var binding: ActivityDetailAkunBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAkunBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        getDetailUser()
        setUpAction()

    }

    private fun getDetailUser(){
        viewModel.getDetailAkun().observe(this){result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        val name = result.data.data?.name
                        val editableTextName = Editable.Factory.getInstance().newEditable(name)
                        binding?.nameEditText?.text = editableTextName
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
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

        binding?.btnUpdateData?.setOnClickListener {
            inputData()
        }
    }
    private fun inputData() {
        val name = binding?.nameEditText?.text.toString()
        val password = binding?.passwordEditText?.text.toString()

        if (name.isEmpty() || password.isEmpty()) {
            showErrorDialog()
        } else{
            viewModel.updateUser(name, password).observe(this){result ->
                if (result != null){
                    when(result){
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                            MaterialAlertDialogBuilder(this)
                                .setTitle(resources.getString(R.string.success_update))
                                .setMessage(resources.getString(R.string.dialog_success_update))
                                .setPositiveButton(resources.getString(R.string.ok)){_, _ ->
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
                    }
                }
            }
        }
    }

    private fun showErrorDialog(){
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.failed_update))
            .setMessage(resources.getString(R.string.dialog_failed_update))
            .setPositiveButton(resources.getString(R.string.ok)){ _, _ ->
            }
            .create()
            .show()
    }
    private fun showLoading(state: Boolean) { binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}