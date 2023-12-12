package com.ndiman.classmath.ui.home.soal

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ndiman.classmath.data.Result
import com.ndiman.classmath.data.pref.QuizListModel
import com.ndiman.classmath.data.pref.TutorialListModel
import com.ndiman.classmath.data.remote.response.DataItem
import com.ndiman.classmath.databinding.ActivityListSoalBinding
import com.ndiman.classmath.ui.ViewModelFactory
import com.ndiman.classmath.ui.home.soal.adapter.ListSoalAdapter
import com.ndiman.classmath.ui.home.soal.viewmodel.ListSoalViewModel

class ListSoalActivity : AppCompatActivity() {

    private val viewModel by viewModels<ListSoalViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val listQuest = ArrayList<QuizListModel>()

    private var idGrade: String? = null
    private var idTutorial: String? = null

    private var binding: ActivityListSoalBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListSoalBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val listSoal = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(ID_SOAL, TutorialListModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(ID_SOAL)
        }

        Log.d("Test", "Lihat ID Grade: ${listSoal?.idGrade}")
        Log.d("Test", "Lihat ID Tutorial: ${listSoal?.idTutorial}")

        if (listSoal != null){

            idGrade = listSoal.idGrade
            idTutorial = listSoal.idTutorial
        }

        setUpAction()
        getTutorialSoal()
    }


    private fun setUpAction(){
        binding?.topAppBar?.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }
    }

    private fun getTutorialSoal(){
        viewModel.getAllSoal(idGrade.toString(),idTutorial.toString()).observe(this){ result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        val newData = result.data.data
                        if (newData.isNotEmpty()){
                            val add= addDataParcelable(newData)
                            listQuest.clear()
                            listQuest.addAll(add)
                            setListSoal()
                        }else{
                            showListNull(true)
                        }
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun addDataParcelable(list: List<DataItem>): ArrayList<QuizListModel>{
        val listSoal = ArrayList<QuizListModel>()
        for (i in list.indices){
            val listTutor = QuizListModel(
                idGrade.toString(),
                idTutorial.toString(),
                list[i].id.toString(),
                list[i].title.toString(),
            )
            listSoal.add(listTutor)
        }
        return listSoal
    }

    private fun setListSoal(){
        val layoutmanager = LinearLayoutManager(this)
        binding?.rvListSoalTutorial?.layoutManager = layoutmanager
        val itemDecoration = DividerItemDecoration(this, layoutmanager.orientation)
        binding?.rvListSoalTutorial?.addItemDecoration(itemDecoration)
        binding?.rvListSoalTutorial?.setHasFixedSize(true)
        val adapter = ListSoalAdapter()
        binding?.rvListSoalTutorial?.adapter = adapter
        adapter.submitList(listQuest)
    }


    companion object{
        const val ID_SOAL = "id_soal"
    }
    private fun showListNull(state: Boolean) { binding?.tvListEmpty?.visibility = if (state) View.VISIBLE else View.GONE }

    private fun showLoading(state: Boolean) { binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}