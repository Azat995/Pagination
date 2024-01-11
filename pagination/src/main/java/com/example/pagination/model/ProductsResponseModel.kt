package com.example.pagination.model

import com.example.pagination.fragments.products.ProductModel
import com.google.gson.annotations.SerializedName

data class ProductsResponseModel(
    @SerializedName("products") val products: List<ProductDetailsResponseModel>,
    @SerializedName("total") val count: Int
)

data class ProductDetailsResponseModel(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: String,
    @SerializedName("rating") val rating: String,
    @SerializedName("brand") val brand: String,
    @SerializedName("images") val images: List<String>
) {
    fun toModel() = ProductModel(id, title, description, price, rating, brand, images)
}