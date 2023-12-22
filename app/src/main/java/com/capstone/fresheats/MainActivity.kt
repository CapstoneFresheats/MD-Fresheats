package com.capstone.fresheats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.capstone.fresheats.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_scan,R.id.navigation_order, R.id.navigation_profile
            )
        )

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main)
        NavigationUI.setupWithNavController(navView, navController)
    }
}