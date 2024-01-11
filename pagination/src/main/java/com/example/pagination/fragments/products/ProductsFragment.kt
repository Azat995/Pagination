package com.example.pagination.fragments.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pagination.databinding.FragmentProductsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductsFragment : Fragment() {

    companion object {
        fun getInstance() = ProductsFragment()
    }

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private val productsViewModel by viewModels<ProductsViewModel>()
    private var productsPagerAdapter: ProductAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setPagingDataObserver()
    }

    private fun setPagingDataObserver() = with(productsViewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            productsFlow.collectLatest { pagingData ->
                productsPagerAdapter?.submitData(pagingData)
            }
        }
    }

    private val loadStateAdapterListener = object : LoadStateAdapterListener {
        override fun retry() {
            productsPagerAdapter?.retry()
        }
    }

    private fun setRecyclerView() = with(binding.productsRecyclerView) {
        productsPagerAdapter = ProductAdapter()
        val footerAdapter = PaginationLoadStateAdapter(loadStateAdapterListener)

        layoutManager = LinearLayoutManager(context)
        adapter = productsPagerAdapter?.withLoadStateFooter(footerAdapter)

//        viewLifecycleOwner.lifecycleScope.launch {
//            productsPagerAdapter?.loadStateFlow?.collectLatest {
////                binding.progressBar.isVisible = (it.refresh is LoadState.Loading) || (it.append is LoadState.Loading)
////                binding.layoutError.isVisible = (it.refresh is LoadState.Error) || (it.append is LoadState.Error)
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        productsPagerAdapter = null
    }
}