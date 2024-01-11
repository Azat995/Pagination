package com.example.pagination.fragments.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pagination.databinding.LayoutPaginationLoadStateBinding

class PaginationLoadStateAdapter(private val adapterListener: LoadStateAdapterListener) : LoadStateAdapter<PaginationLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        LoadStateViewHolder(LayoutPaginationLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) = with(holder.binding) {
        val isLoading = loadState is LoadState.Loading

        loadStateRetryButton.isVisible = !isLoading
        loadStateErrorMessage.isVisible = !isLoading
        loadStateProgress.isVisible = isLoading

        if (loadState is LoadState.Error) loadStateErrorMessage.text = loadState.error.localizedMessage

        loadStateRetryButton.setOnClickListener { adapterListener.retry() }
    }

    class LoadStateViewHolder(val binding: LayoutPaginationLoadStateBinding) : RecyclerView.ViewHolder(binding.root)
}

interface LoadStateAdapterListener {
    fun retry()
}