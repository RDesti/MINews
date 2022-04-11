package com.example.minews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.minews.R
import com.example.minews.adapter.MainActivityAdapter
import com.example.minews.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val _viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private var _adapter: MainActivityAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        _binding.lifecycleOwner = this

        //need for viewModel init
        _viewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }
}