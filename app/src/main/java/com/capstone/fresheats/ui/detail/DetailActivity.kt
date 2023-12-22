package com.capstone.fresheats.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.capstone.fresheats.R
import com.capstone.fresheats.databinding.ActivityDetailBinding
import com.capstone.fresheats.model.response.home.Data

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtra<Data>("data")?.let { data ->
            val bundle = Bundle().apply {
                putParcelable("data", data)
            }

            val detailFragment = DetailFragment()
            detailFragment.arguments = bundle

            val navHostFragment = supportFragmentManager.findFragmentById(R.id.detailHostFragment) as NavHostFragment
            navController = navHostFragment.navController

//            supportFragmentManager.beginTransaction()
//                .replace(R.id.detailHostFragment, detailFragment)
//                .commit()
        }
    }

    fun toolbarPayment() {
        binding.includeToolbar.toolbar.visibility = View.VISIBLE
        binding.includeToolbar.toolbar.title = "Pembayaran"
        binding.includeToolbar.toolbar.subtitle = "Makanan Terbaik Untuk Anda"
        binding.includeToolbar.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_000, null)
        binding.includeToolbar.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun toolbarDetail() {
        if (::binding.isInitialized) {
            binding.includeToolbar.toolbar.visibility = View.GONE
        }
    }
}