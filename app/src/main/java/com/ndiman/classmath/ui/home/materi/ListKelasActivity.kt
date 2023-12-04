package com.ndiman.classmath.ui.home.materi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ndiman.classmath.databinding.ActivityListKelasBinding

class ListKelasActivity : AppCompatActivity() {

    private var binding: ActivityListKelasBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListKelasBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}