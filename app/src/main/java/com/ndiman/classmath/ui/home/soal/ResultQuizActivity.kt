package com.ndiman.classmath.ui.home.soal

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ndiman.classmath.data.Result
import com.ndiman.classmath.data.pref.ResultQuizBody
import com.ndiman.classmath.data.remote.response.DataResultQuiz
import com.ndiman.classmath.databinding.ActivityResultQuizBinding
import com.ndiman.classmath.ui.MainActivity
import com.ndiman.classmath.ui.ViewModelFactory
import com.ndiman.classmath.ui.home.soal.viewmodel.ResultQuizViewModel

class ResultQuizActivity : AppCompatActivity() {

    private val viewModel by viewModels<ResultQuizViewModel> {
        ViewModelFactory.getInstance(this)

    }
    private var binding: ActivityResultQuizBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultQuizBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val result = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_RESULT, ResultQuizBody::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_RESULT)
        }

        if (result != null){
            viewModel.getResultQuiz(result.username, result.idQuiz, result.answers).observe(this){result ->
                if (result!=null){
                    when(result){
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            val data = result.data.data
                            setData(data)
                        }
                        is Result.Error -> {
                            showLoading(false)
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        setUpAction()
    }

    private fun setData(data: DataResultQuiz?){
        binding?.apply {
            valueQuiz.text = data?.score.toString()
            valueCorrect.text = data?.correct.toString()
            valueWrong.text = data?.wrong.toString()
            valueNoAnswer.text = data?.notAnswered.toString()
        }
    }


    private fun setUpAction(){
        binding?.btnBack?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun showLoading(state: Boolean){binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE}

    companion object{
        const val EXTRA_RESULT = "extra_result"
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}