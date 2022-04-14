package com.example.minews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.minews.R
import com.example.minews.databinding.ItemNewsBinding
import com.example.minews.entity.TopContentModel
import com.squareup.picasso.Picasso

class MainActivityAdapter(
    private val thumbnailClickListener: (TopContentModel) -> Unit,
    private val downloadButtonClickListener: (TopContentModel) -> Unit
) : PagingDataAdapter<TopContentModel, MainActivityAdapter.MainViewHolder>(DataDiffItemCallback) {

    class MainViewHolder(val newsBinding: ItemNewsBinding) :
        RecyclerView.ViewHolder(newsBinding.root) {

        fun bind(
            topContentModel: TopContentModel,
            thumbnailClickListener: (TopContentModel) -> Unit,
            downloadButtonClickListener: (TopContentModel) -> Unit
        ) {
            if (!topContentModel.thumbnailUrl.isNullOrEmpty()) {
                newsBinding.contentPreviewImage.visibility = View.VISIBLE
                Picasso.get().load(topContentModel.thumbnailUrl).into(newsBinding.contentPreviewImage)
            } else {
                newsBinding.contentPreviewImage.visibility = View.GONE
            }

            newsBinding.contentPreviewImage.setOnClickListener {
                thumbnailClickListener(topContentModel)
            }

            newsBinding.fileDownloadImage.setOnClickListener {
                downloadButtonClickListener(topContentModel)
            }
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.newsBinding.topContentModel = getItem(position)
        getItem(position)?.let {
            holder.bind(
                it,
                thumbnailClickListener,
                downloadButtonClickListener
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_news,
            parent,
            false
        )
    )

    private object DataDiffItemCallback : DiffUtil.ItemCallback<TopContentModel>() {
        override fun areItemsTheSame(oldItem: TopContentModel, newItem: TopContentModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TopContentModel,
            newItem: TopContentModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}