package com.example.minews.ui

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minews.R
import com.example.minews.adapter.MainActivityAdapter
import com.example.minews.adapter.MainLoadStateAdapter
import com.example.minews.databinding.ActivityMainBinding
import com.example.minews.entity.TopContentModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val _viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private var _adapter: MainActivityAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        _binding.lifecycleOwner = this

        initAdapter()
        initViewModel()
    }

    private fun initViewModel() {
        lifecycleScope.launchWhenCreated {
            _viewModel.getData().collectLatest {
                _adapter?.submitData(it)
            }
        }
    }

    private fun initAdapter() {
        _adapter = MainActivityAdapter({ model -> clickCardThumbnail(model) },
            { model -> clickFileDownload(model) })
        _binding.recycler.layoutManager = LinearLayoutManager(applicationContext)
        _binding.recycler.adapter = _adapter?.withLoadStateHeaderAndFooter(
            header = MainLoadStateAdapter { _adapter?.retry() },
            footer = MainLoadStateAdapter { _adapter?.retry() }
        )

        _adapter?.addLoadStateListener { state: CombinedLoadStates ->
            _binding.recycler.isVisible = state.refresh != LoadState.Loading
            _binding.loadingAnimation.isVisible = state.refresh == LoadState.Loading

        }
    }

    private fun clickCardThumbnail(model: TopContentModel) {
        if (!model.fullFileUrl.isNullOrEmpty()) {
            openFileUrl(model.fullFileUrl ?: return)
        }
    }

    private fun clickFileDownload(model: TopContentModel) {
        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri =
            Uri.parse(model.fullFileUrl)
        val type = model.fullFileUrl?.substringAfterLast('.')

        if ((type?.length ?: return) < 4) {
            val request = DownloadManager.Request(uri)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    "MINews_${model.id}.$type"
                )
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setAllowedOverMetered(true)
            val reference: Long = manager.enqueue(request)

            val receiver = object : BroadcastReceiver() {
                override fun onReceive(p0: Context?, p1: Intent?) {
                    val id: Long? = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                    if (id == reference)
                        Toast.makeText(
                            applicationContext, "Downloading File",
                            Toast.LENGTH_LONG
                        ).show()
                }
            }
            registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        } else {
            Toast.makeText(
                applicationContext, "Not Downloading File",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun openFileUrl(url: String) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        startActivity(openURL)
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }
}