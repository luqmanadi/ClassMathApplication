package com.ndiman.classmath.ui.home.soal

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ndiman.classmath.BuildConfig
import com.ndiman.classmath.R
import com.ndiman.classmath.data.Result
import com.ndiman.classmath.data.pref.QuizListModel
import com.ndiman.classmath.data.pref.ResultQuizBody
import com.ndiman.classmath.data.remote.response.QuestionsItem
import com.ndiman.classmath.databinding.ActivitySoalBinding
import com.ndiman.classmath.ui.ViewModelFactory
import com.ndiman.classmath.ui.home.soal.viewmodel.SoalViewModel

class SoalActivity : AppCompatActivity() {

    private val viewModel by viewModels<SoalViewModel> {
        ViewModelFactory.getInstance(this)

    }
    private var idGrade: String = ""
    private var idTutorial: String = ""
    private var idQuiz: String = ""
    private var username: String = ""
    private var questSize: String? = null
    private var currentQuestionIndex = 0
    private lateinit var listQuestion: List<QuestionsItem>
    private var binding: ActivitySoalBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoalBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val soalId = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_ID_QUIZ, QuizListModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_ID_QUIZ)
        }

        viewModel.getSession().observe(this){result->
            username = result.username
        }

        idGrade = soalId!!.idGrade
        idTutorial = soalId.idTutorial
        idQuiz = soalId.idQuiz

        getQuiz()
        setUpAction()


    }

    private fun getQuiz(){
        viewModel.getQuizezz(idGrade, idTutorial, idQuiz).observe(this){result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        questSize = result.data.data?.questions?.size.toString()
                        val size = result.data.data?.questions?.size ?: 0
                        viewModel.initQuestSize(size)
                        listQuestion = result.data.data?.questions!!
                        setQuestFirst()
                        showLoading(false)
                    }
                    is Result.Error ->{
                        showLoading(false)
                        Toast.makeText(this, "${result.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun setQuestFirst(){
        currentQuestionIndex = 0
        showQuestion(currentQuestionIndex)
    }

    private fun showQuestion(index: Int) {
        binding?.apply {
            tvNoSoal.text = "${index + 1}/$questSize"
            val currentQuestion = listQuestion[index]
            if (currentQuestion.questionFile != null) {
                // Tampilkan gambar soal
                imageSoal.visibility = View.VISIBLE
                Glide.with(this@SoalActivity)
                    .load(uriImage+listQuestion[index].questionFile)
                    .into(imageSoal)
            } else {
                // Sembunyikan gambar soal jika tidak ada
                imageSoal.visibility = View.GONE
            }
            tvTitleSoal.text = currentQuestion.content
            answer1.text = currentQuestion.choices!![0]
            answer2.text = currentQuestion.choices[1]
            answer3.text = currentQuestion.choices[2]
            answer4.text = currentQuestion.choices[3]

            val answers = viewModel.getAnswers()
            val selectedAnswer = if (index < answers.size) answers[index] else null
            when(selectedAnswer){
                "A" -> radioGroupChoice.check(R.id.answer1)
                "B" -> radioGroupChoice.check(R.id.answer2)
                "C" -> radioGroupChoice.check(R.id.answer3)
                "D" -> radioGroupChoice.check(R.id.answer4)
                else -> radioGroupChoice.clearCheck()
            }
        }
    }

    private fun setUpAction(){

        binding?.apply {
            btnPrev.setOnClickListener {
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--
                    showQuestion(currentQuestionIndex)
                }
            }
            btnForward.setOnClickListener {
                if (currentQuestionIndex < listQuestion.size - 1) {
                    currentQuestionIndex++
                    showQuestion(currentQuestionIndex)
                }
            }
            radioGroupChoice.setOnCheckedChangeListener{_, checkedId ->
                val selectedAnswer: String? = when(checkedId){
                    R.id.answer1 -> "A"
                    R.id.answer2 -> "B"
                    R.id.answer3 -> "C"
                    R.id.answer4 -> "D"
                    else -> null
                }
                viewModel.saveAnswer(currentQuestionIndex, selectedAnswer)
            }
            btnSimpan.setOnClickListener {
                val answers = viewModel.getAnswers()
                val postQuiz = ResultQuizBody(username, idQuiz, answers)
                MaterialAlertDialogBuilder(this@SoalActivity)
                    .setTitle(resources.getString(R.string.finish))
                    .setMessage(resources.getString(R.string.dialog_quiz))
                    .setPositiveButton(resources.getString(R.string.yes)){_, _ ->
                        val intent = Intent(this@SoalActivity, ResultQuizActivity::class.java)
                        intent.putExtra(ResultQuizActivity.EXTRA_RESULT, postQuiz)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    .setNegativeButton(resources.getString(R.string.no)){ _, _ ->
                    }
                    .create()
                    .show()
            }
        }

    }

    private fun showLoading(state: Boolean) { binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE }



    companion object{
        const val uriImage = BuildConfig.BASE_URI_PHOTO
        const val EXTRA_ID_QUIZ = "extra_id_quiz"
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}