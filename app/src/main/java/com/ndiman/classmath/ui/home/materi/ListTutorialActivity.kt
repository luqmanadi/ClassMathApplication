package com.ndiman.classmath.ui.home.materi

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ndiman.classmath.data.Result
import com.ndiman.classmath.data.pref.FilePdf
import com.ndiman.classmath.data.remote.response.DataItemTutorial
import com.ndiman.classmath.databinding.ActivityListTutorialBinding
import com.ndiman.classmath.ui.ViewModelFactory
import com.ndiman.classmath.ui.home.materi.adapter.ListTutorialAdapter
import com.ndiman.classmath.ui.home.materi.viewmodel.ListTutorialViewModel

class ListTutorialActivity : AppCompatActivity() {

    private val viewModel by viewModels<ListTutorialViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val listTutorialClass = ArrayList<FilePdf>()


    private var idGrade: String = ""

    private var binding: ActivityListTutorialBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListTutorialBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        idGrade = intent.getStringExtra(EXTRA_ID_GRADE)!!

        getData()
        setUpAction()
    }

    private fun getData(){
        viewModel.getAllTutorial(idGrade).observe(this){result->
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
                            listTutorialClass.clear()
                            listTutorialClass.addAll(add)
                            setList()
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

    private fun addDataParcelable(list: List<DataItemTutorial>): ArrayList<FilePdf>{
        val listTutorial = ArrayList<FilePdf>()
        for (i in list.indices){
            val story = FilePdf(
                list[i].id.toString(),
                list[i].grade?.id.toString(),
                list[i].grade?.name.toString(),
                list[i].title.toString(),
                list[i].tutorialImage.toString(),
                list[i].tutorialFile.toString(),
            )
            listTutorial.add(story)
        }
        return listTutorial
    }
    private fun setList(){
        val layoutmanager = LinearLayoutManager(this)
        binding?.rvListTutorial?.layoutManager = layoutmanager
        val itemDecoration = DividerItemDecoration(this, layoutmanager.orientation)
        binding?.rvListTutorial?.addItemDecoration(itemDecoration)
        binding?.rvListTutorial?.setHasFixedSize(true)
        val adapter = ListTutorialAdapter()
        binding?.rvListTutorial?.adapter = adapter
        adapter.submitList(listTutorialClass)
    }
    private fun setUpAction(){
        binding?.topAppBar?.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }
    }

    private fun showListNull(state: Boolean) { binding?.tvListEmpty?.visibility = if (state) View.VISIBLE else View.GONE }

    private fun showLoading(state: Boolean) { binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object{
        const val EXTRA_ID_GRADE = "extra_id_grade"
    }
}