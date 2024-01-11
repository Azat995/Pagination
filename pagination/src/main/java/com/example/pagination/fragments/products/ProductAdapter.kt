package com.example.pagination.fragments.products

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.pagination.databinding.ItemProductBinding

class ProductAdapter : PagingDataAdapter<ProductModel, ProductAdapter.ProductViewHolder>(ProductDiffUtilsCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(productModel: ProductModel) = with(binding) {
            productModel.apply {
                titleTextView.text = title
                ratingTextView.text = rating
                brandTextView.text = brand
                priceTextView.text = "$price$"
                descriptionTextView.text = description

                imagesRecyclerView.apply recyclerView@{
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = ProductImageAdapter().apply { submitList(images) }
                    if (onFlingListener == null) PagerSnapHelper().apply { attachToRecyclerView(this@recyclerView) }
                }
            }
        }
    }

    class ProductDiffUtilsCallback : DiffUtil.ItemCallback<ProductModel>() {
        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem == newItem
        }

    }
}
