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
import com.example.minews.network.parseModels.Children
import com.squareup.picasso.Picasso

class MainActivityAdapter :
    PagingDataAdapter<TopContentModel, MainActivityAdapter.MainViewHolder>(DataDiffItemCallback) {

    class MainViewHolder(val newsBinding: ItemNewsBinding) :
        RecyclerView.ViewHolder(newsBinding.root) {

        fun bind(topContentModel: TopContentModel) {
            if (!topContentModel.thumbnail.isNullOrEmpty()) {
                newsBinding.contentPreviewImage.visibility = View.VISIBLE
                Picasso.get().load(topContentModel.thumbnail).into(newsBinding.contentPreviewImage)
            } else {
                newsBinding.contentPreviewImage.visibility = View.GONE
            }
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.newsBinding.topContentModel = getItem(position)
        holder.itemView.setOnClickListener {
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