package com.example.pagination.fragments.products

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pagination.databinding.ItemProductImageViewBinding

class ProductImageAdapter : ListAdapter<String, ProductImageAdapter.ProductImageViewHolder>(ProductDiffUtilsCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductImageViewHolder {
        return ProductImageViewHolder(ItemProductImageViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProductImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductImageViewHolder(private val binding: ItemProductImageViewBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(imageUrl: String) = with(binding) {
            productImageView.load(imageUrl)
        }
    }

    class ProductDiffUtilsCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
