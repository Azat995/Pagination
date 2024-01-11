package com.example.pagination.fragments.products

data class ProductModel(
    val id: Long,
    val title: String,
    val description: String,
    val price: String,
    val rating: String,
    val brand: String,
    val images: List<String>
)