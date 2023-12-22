package com.example.calorietracker_uas.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.calorietracker_uas.R
import com.example.calorietracker_uas.data.room.CustomFoodViewModel
import com.example.calorietracker_uas.data.room.FoodDao
import com.example.calorietracker_uas.data.room.FoodDatabase
import com.example.calorietracker_uas.data.room.FoodRepository
import com.example.calorietracker_uas.ui.main.addmeal.AddMealActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {


    companion object {
        private lateinit var executorService: ExecutorService
        lateinit var mFoodDao: FoodDao
        private lateinit var foodRepository: FoodRepository
        val foodViewModelRoom by lazy {
            CustomFoodViewModel(foodRepository)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        executorService = Executors.newSingleThreadExecutor()
        val db = FoodDatabase.getDatabase(this)
        mFoodDao = db.foodDao()
        foodRepository = FoodRepository(mFoodDao)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddMealActivity::class.java)
            startActivity(intent)
        }
        bottomBar()
    }

    private fun bottomBar() {
        val bottomBar = findViewById<BottomNavigationView>(R.id.bottom_bar)
        bottomBar.background = null
        bottomBar.menu.getItem(2).isEnabled = false

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        NavigationUI.setupWithNavController(
            bottomBar,
            navHostFragment.navController
        )

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.viewPagerFragment) {
                bottomAppBar.visibility = View.GONE
                fab.visibility = View.GONE
            } else {
                bottomAppBar.visibility = View.VISIBLE
                fab.visibility = View.VISIBLE
            }
        }
    }
}