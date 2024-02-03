package com.synrgyacademy.finalproject

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ** setup bottom navigation
        navView = findViewById(R.id.nav_view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->

            /**
             * * Here's where we hide bottom navbar if needed
             * ? Documentation : https://developer.android.com/guide/navigation/navigation-ui
             */

            when (destination.id) {
                //? id action_xx is only fragment id for bottom navigation bar.
                //? check menu and main_nav.
                R.id.ticket_navigation -> {
                    visible()
                }

                R.id.populer_places -> {
                    visible()
                }

                R.id.order_navigation -> {
                    visible()
                }

                R.id.profile_navigation -> {
                    visible()
                }

                else -> {
                    invisible()
                }
            }
        }
    }


    private fun visible() {
        val navView = this.findViewById<BottomNavigationView>(R.id.nav_view)
        navView.visibility = View.VISIBLE
    }

    private fun invisible() {
        val navView = this.findViewById<BottomNavigationView>(R.id.nav_view)
        navView.visibility = View.GONE
    }

}