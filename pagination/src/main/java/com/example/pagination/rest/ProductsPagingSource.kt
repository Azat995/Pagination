package com.example.pagination.rest

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagination.fragments.products.ProductModel
import com.example.pagination.model.ProductsResponseModel
import com.example.pagination.utils.isNetworkAvailable
import java.sql.Timestamp
import java.util.Calendar
import java.util.Date

class ProductsPagingSource: PagingSource<Int, ProductModel>() {

    private var offset = 0

    override fun getRefreshKey(state: PagingState<Int, ProductModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductModel> {
        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize

        return if (isNetworkAvailable()) {
            val productsResponseModel = getProductsData(offset, pageSize)
            if (productsResponseModel != null) {
                val products: List<ProductModel> = productsResponseModel.products.map { it.toModel() }

                val nextKey = if (products.size < pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1
                offset += products.size

                LoadResult.Page(products, prevKey, nextKey)
            } else {
                LoadResult.Error(NullPointerException())
            }
        } else {
            val stamp = Timestamp(System.currentTimeMillis())
            val date = Date(stamp.time)

            LoadResult.Error(Throwable("No internet connection"))
        }
    }

    private suspend fun getProductsData(offset: Int, limit: Int): ProductsResponseModel? {
        val response = ApiFactory.productsApi.getProducts(offset, limit)
        return if (response.isSuccessful) response.body()
        else null
    }
}
