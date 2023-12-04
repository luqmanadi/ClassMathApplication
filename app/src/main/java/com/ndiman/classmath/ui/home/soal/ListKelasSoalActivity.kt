package com.ndiman.classmath.ui.home.soal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ndiman.classmath.databinding.ActivityListKelasSoalBinding

class ListKelasSoalActivity : AppCompatActivity() {

    private var binding: ActivityListKelasSoalBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListKelasSoalBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }


}