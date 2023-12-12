package com.ndiman.classmath.ui.home.soal

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ndiman.classmath.data.Result
import com.ndiman.classmath.data.pref.TutorialListModel
import com.ndiman.classmath.data.remote.response.DataItemTutorial
import com.ndiman.classmath.databinding.ActivityListTutorialBinding
import com.ndiman.classmath.ui.ViewModelFactory
import com.ndiman.classmath.ui.home.soal.adapter.ListTutorialSoalAdapter
import com.ndiman.classmath.ui.home.soal.viewmodel.ListSoalTutorialViewModel

class ListSoalTutorialActivity : AppCompatActivity() {

    private val viewModel by viewModels<ListSoalTutorialViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val listTutorialSoal = ArrayList<TutorialListModel>()

    private var idGrade: String? = null

    private var binding : ActivityListTutorialBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListTutorialBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        idGrade = intent.getStringExtra(ID_GRADE_SOAL)

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
        viewModel.getAllTutorial(idGrade.toString()).observe(this){ result ->
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
                            listTutorialSoal.clear()
                            listTutorialSoal.addAll(add)
                            setListTutorialSoal()
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

    private fun addDataParcelable(list: List<DataItemTutorial>): ArrayList<TutorialListModel>{
        val listTutorial = ArrayList<TutorialListModel>()
        for (i in list.indices){
            val listTutor = TutorialListModel(
                list[i].grade?.id.toString(),
                list[i].id.toString(),
                list[i].tutorialImage.toString(),
                list[i].title.toString(),
            )
            listTutorial.add(listTutor)
        }
        return listTutorial
    }

    private fun setListTutorialSoal(){
        val layoutmanager = LinearLayoutManager(this)
        binding?.rvListTutorial?.layoutManager = layoutmanager
        val itemDecoration = DividerItemDecoration(this, layoutmanager.orientation)
        binding?.rvListTutorial?.addItemDecoration(itemDecoration)
        binding?.rvListTutorial?.setHasFixedSize(true)
        val adapter = ListTutorialSoalAdapter()
        binding?.rvListTutorial?.adapter = adapter
        adapter.submitList(listTutorialSoal)
    }

    companion object{
        const val ID_GRADE_SOAL = "id_grade_soal"
    }
    private fun showListNull(state: Boolean) { binding?.tvListEmpty?.visibility = if (state) View.VISIBLE else View.GONE }

    private fun showLoading(state: Boolean) { binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}