package com.example.pagination.rest


object ApiFactory {
    val productsApi: ProductsApi = NetworkModule.dummyJsonRetrofit().create(ProductsApi::class.java)
}