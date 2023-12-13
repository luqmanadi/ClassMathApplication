package com.ndiman.classmath.ui.profile.favoritmateri

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ndiman.classmath.data.local.entity.FavoritMateri
import com.ndiman.classmath.databinding.ActivityFavoritMateriBinding
import com.ndiman.classmath.ui.ViewModelFactory

class FavoritMateriActivity : AppCompatActivity() {

    private val viewModel by viewModels<FavoriteMateriViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var binding: ActivityFavoritMateriBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritMateriBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpAction()
        getAllFavoritMateri()
    }

    private fun setUpAction(){
        binding?.topAppBar?.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }
    }

    private fun getAllFavoritMateri(){
        viewModel.getFavoritMateri().observe(this){listFavorit ->
            showLoading(false)
            if (listFavorit.isNotEmpty()) {
                showListNull(false)
                setListFavoritMateri(listFavorit)
            } else {
                showListNull(true)
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setListFavoritMateri(listFavorit: List<FavoritMateri>){
        val layoutmanager = LinearLayoutManager(this)
        binding?.rvListFavorit?.layoutManager = layoutmanager
        val itemDecoration = DividerItemDecoration(this, layoutmanager.orientation)
        binding?.rvListFavorit?.addItemDecoration(itemDecoration)
        binding?.rvListFavorit?.setHasFixedSize(true)
        val adapter = FavoritMateriAdapter()
        binding?.rvListFavorit?.adapter = adapter
        adapter.submitList(listFavorit)

        viewModel.getFavoritMateri().observe(this) { updatedList ->
            adapter.submitList(updatedList)
            adapter.notifyDataSetChanged()
        }
    }


    private fun showListNull(state: Boolean) { binding?.tvListEmpty?.visibility = if (state) View.VISIBLE else View.GONE }

    private fun showLoading(state: Boolean) { binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}