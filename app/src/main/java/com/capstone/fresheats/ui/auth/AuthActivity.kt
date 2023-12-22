package com.capstone.fresheats.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.capstone.fresheats.R
import com.capstone.fresheats.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pageRequest = intent.getIntExtra("page_request", 0)
        if (pageRequest == 2) {
            tolbarSignUp()
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.fragmentSignIn, true)
                .build()

            val authHostFragment = supportFragmentManager.findFragmentById(R.id.authHostFragment) as NavHostFragment
            Navigation.findNavController(authHostFragment.requireView())
                .navigate(R.id.action_signup, null, navOptions)
        }
    }

    fun tolbarSignUp() {
        binding.layoutToolbar.toolbar.title = "Sign Up"
        binding.layoutToolbar.toolbar.subtitle = "Pastikan data anda benar"
        binding.layoutToolbar.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_000, null)
        binding.layoutToolbar.toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}