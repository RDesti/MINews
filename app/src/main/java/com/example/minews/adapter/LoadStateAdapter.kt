package com.example.minews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.minews.R
import com.example.minews.databinding.ItemLoadStateFooterViewBinding

class MainLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<MainLoadStateAdapter.MainLoadStateViewHolder>() {

    class MainLoadStateViewHolder(
        val itemFooterBinding: ItemLoadStateFooterViewBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(itemFooterBinding.root) {

        init {
            itemFooterBinding.errorText.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            itemFooterBinding.errorConstraint.isVisible = loadState is LoadState.Error
            itemFooterBinding.loadingAnimation.isVisible = loadState is LoadState.Loading
        }
    }

    override fun onBindViewHolder(holder: MainLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MainLoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_load_state_footer_view, parent, false)
        val binding = ItemLoadStateFooterViewBinding.bind(view)
        return MainLoadStateViewHolder(binding, retry)
    }
}