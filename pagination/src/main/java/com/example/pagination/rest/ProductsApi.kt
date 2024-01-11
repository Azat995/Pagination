package com.example.pagination.rest

import com.example.pagination.model.ProductsResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApi {

    @GET("products")
    suspend fun getProducts(@Query("skip") offset: Int, @Query("limit") limit: Int): Response<ProductsResponseModel>
}