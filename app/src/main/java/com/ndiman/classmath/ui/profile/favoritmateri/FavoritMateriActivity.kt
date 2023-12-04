package com.ndiman.classmath.ui.profile.favoritmateri

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
    }


    private fun setUpAction(){
        binding?.topAppBar?.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}