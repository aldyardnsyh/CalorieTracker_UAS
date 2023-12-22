package com.example.calorietracker_uas.ui.main.addmeal

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker_uas.data.room.CustomFoodViewModel
import com.example.calorietracker_uas.data.room.FoodDao
import com.example.calorietracker_uas.data.room.FoodDatabase
import com.example.calorietracker_uas.data.room.FoodRepository
import com.example.calorietracker_uas.databinding.ActivityAddMealBinding
import com.example.calorietracker_uas.ui.main.MainActivity
import com.example.calorietracker_uas.ui.main.food.FoodViewModel

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AddMealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMealBinding
    private val foodViewModel by lazy { FoodViewModel(this@AddMealActivity) }
    private lateinit var adapter: AddMealAdapter
    private lateinit var rv: RecyclerView
    private lateinit var executorService: ExecutorService
    private lateinit var mFoodDao: FoodDao
    private lateinit var foodRepository: FoodRepository

    private val foodViewModelRoom by lazy {
        CustomFoodViewModel(foodRepository)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rv = binding.rvAddMeal
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv.layoutManager = linearLayoutManager

        executorService = Executors.newSingleThreadExecutor()
        val db = FoodDatabase.getDatabase(this)
        mFoodDao = db.foodDao()
        foodRepository = FoodRepository(mFoodDao)

        foodObserver()

        binding.btnEat.setOnClickListener {
            val intent = Intent(this@AddMealActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "Selamat Makan!!", Toast.LENGTH_SHORT).show()
        }

//        binding.btnCustomFood.setOnClickListener {
//            // Tambahkan log atau Toast di sini untuk memastikan bahwa onClick bekerja.
////            val intent = Intent(this@AddMealActivity, CustomFoodViewModel::class.java)
////            startActivity(intent)
////            finish()
//            val customFoodDialog = CustomFoodDialog(foodViewModelRoom)
//            customFoodDialog.show(supportFragmentManager, "Custom Food Dialog")
//        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun foodObserver() {
        foodViewModel.foodList.observe(this) {
            adapter = AddMealAdapter(it, this@AddMealActivity)
            rv.adapter = adapter
        }
    }
}