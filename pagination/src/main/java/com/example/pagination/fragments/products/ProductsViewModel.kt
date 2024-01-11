package com.example.pagination.fragments.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pagination.rest.ApiFactory
import com.example.pagination.rest.ProductsPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {

    private val productsApi = ApiFactory.productsApi

    fun getProducts(successCallback: (products: List<ProductModel>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = productsApi.getProducts(0, Int.MAX_VALUE)
            if (response.isSuccessful && response.body() != null) {
                val products: List<ProductModel> = response.body()?.products?.map { it.toModel() } ?: return@launch
                launch(Dispatchers.Main) {
                    successCallback.invoke(products)
                }
            }
        }
    }


    /** Pagination */
    private lateinit var productsPagingSource: ProductsPagingSource

    var productsFlow: Flow<PagingData<ProductModel>> = Pager(
        PagingConfig(pageSize = PAGINATION_LIMIT, enablePlaceholders = true),
        pagingSourceFactory = {
            ProductsPagingSource().also { productsPagingSource = it }
        }
    ).flow.cachedIn(viewModelScope)

    fun invalidatePaging() {
        productsPagingSource.invalidate()
    }

    companion object {
        private const val PAGINATION_LIMIT = 2
    }
}