package com.ndiman.classmath.ui.home.materi

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ndiman.classmath.data.Result
import com.ndiman.classmath.data.remote.response.DataItemGrade
import com.ndiman.classmath.databinding.ActivityListKelasBinding
import com.ndiman.classmath.ui.ViewModelFactory
import com.ndiman.classmath.ui.home.materi.adapter.ListGradeMateriAdapter
import com.ndiman.classmath.ui.home.materi.viewmodel.ListKelasViewModel

class ListKelasActivity : AppCompatActivity() {

    private val viewModel by viewModels<ListKelasViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var binding: ActivityListKelasBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListKelasBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpAction()
        getGradeMateri()
    }

    private fun setUpAction(){
        binding?.topAppBar?.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }
    }
    private fun getGradeMateri(){
        viewModel.getGradeMateri().observe(this){result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        val newData = result.data.data
                        if (newData.isNotEmpty()){
                            setListGrade(newData)
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
    private fun setListGrade(listGrade: List<DataItemGrade>){
        val layoutmanager = LinearLayoutManager(this)
        binding?.rvListClass?.layoutManager = layoutmanager
        val itemDecoration = DividerItemDecoration(this, layoutmanager.orientation)
        binding?.rvListClass?.addItemDecoration(itemDecoration)
        binding?.rvListClass?.setHasFixedSize(true)
        val adapter = ListGradeMateriAdapter()
        binding?.rvListClass?.adapter = adapter
        adapter.submitList(listGrade)
    }


    private fun showListNull(state: Boolean) { binding?.tvListEmpty?.visibility = if (state) View.VISIBLE else View.GONE }

    private fun showLoading(state: Boolean) { binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}