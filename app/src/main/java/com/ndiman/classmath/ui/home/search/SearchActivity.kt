package com.ndiman.classmath.ui.home.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ndiman.classmath.data.Result
import com.ndiman.classmath.data.remote.response.TutorialItem
import com.ndiman.classmath.databinding.ActivitySearchBinding
import com.ndiman.classmath.ui.ViewModelFactory
import com.ndiman.classmath.ui.home.search.adapter.SearchAdapter
import com.ndiman.classmath.ui.home.search.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private val viewModel by viewModels<SearchViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var getString: String? = null
    private var binding: ActivitySearchBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        getString = intent.getStringExtra(EXTRA_QUERY)
        setUpAction()
        searchFirst()
    }


    private fun setUpAction(){
        binding?.apply {
            topAppBar.setNavigationOnClickListener {
                @Suppress("DEPRECATION")
                onBackPressed()
            }
        }
    }

    private fun searchFirst(){
        viewModel.getSearch(getString.toString()).observe(this){result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        val dataListTutor = result.data.data?.tutorial
                        if (dataListTutor!!.isNotEmpty()){
                            showLoading(false)
                            setSearch(dataListTutor)
                        }else{
                            showLoading(false)
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

    private fun setSearch(listTutor: List<TutorialItem>){
        val layoutmanager = LinearLayoutManager(this)
        binding?.rvSearch?.layoutManager = layoutmanager
        val itemDecoration = DividerItemDecoration(this, layoutmanager.orientation)
        binding?.rvSearch?.addItemDecoration(itemDecoration)
        binding?.rvSearch?.setHasFixedSize(true)
        val adapter = SearchAdapter()
        binding?.rvSearch?.adapter = adapter
        adapter.submitList(listTutor)
    }

    private fun showListNull(state: Boolean) { binding?.tvListEmpty?.visibility = if (state) View.VISIBLE else View.GONE }

    private fun showLoading(state: Boolean) { binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE }


    companion object{
        const val EXTRA_QUERY = "extra_query"
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}