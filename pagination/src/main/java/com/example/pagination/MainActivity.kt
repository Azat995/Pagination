package com.example.pagination

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pagination.databinding.ActivityMainBinding
import com.example.pagination.fragments.products.ProductsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openFragment(ProductsFragment.getInstance())
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(R.id.fragments_container_layout, fragment, fragment.javaClass.simpleName)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }
}