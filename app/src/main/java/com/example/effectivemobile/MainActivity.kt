package com.example.effectivemobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.effectivemobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализация NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Настройка BottomNavigationView с NavController
        binding.bottomNavigation.setupWithNavController(navController)

        // Настройка NavController для использования с BottomNavigationView
        setupNavController()
    }

    private fun setupNavController() {
        // Устанавливаем граф навигации
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navController.graph = navGraph

        // Настройка взаимодействия с BottomNavigationView
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_search -> {
                    navController.navigate(R.id.mainFragment)
                    true
                }
                R.id.navigation_favorites -> {
                    navController.navigate(R.id.favoritesFragment)
                    true
                }
                R.id.navigation_responses -> {
                    navController.navigate(R.id.applyFragment)
                    true
                }
                else -> false
            }
        }
    }
}
