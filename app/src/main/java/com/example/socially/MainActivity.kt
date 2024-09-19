package com.example.socially

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val navHostFragment:  NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)


//        val navController = findNavController(R.id.nav_host_fragment)

        // Setup navbar (all bottom 3 options seem to work)
        // Option1
//        bottomNavigationView.setupWithNavController(navController)

        // Option2
//        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        //Option3
//        bottomNavigationView.setOnItemSelectedListener { item ->
//            when(item.itemId) {
//                R.id.nav_home -> {
//                    navController.navigate(R.id.nav_home)
//                    true
//                }
//                R.id.nav_events -> {
//                    navController.navigate(R.id.nav_events)
//                    true
//                }
//                R.id.nav_profile -> {
//                    navController.navigate(R.id.nav_profile)
//                    true
//                }
//                else -> false
//            }
//        }

    }
}