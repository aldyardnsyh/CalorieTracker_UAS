package com.example.calorietracker_uas.data.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calorietracker_uas.data.Food
import kotlinx.coroutines.launch

class CustomFoodViewModel(private val repository: FoodRepository) : ViewModel() {

    // Fungsi untuk menyimpan data makanan ke Room
    fun saveFoodToRoom(food: Food) {
        val foodEntity = FoodEntity(
            name = food.name.toString(),
            calorie = food.calorie,
            url = food.url,
            protein = food.protein,
            fat = food.fat,
            carbohydrate = food.carbohydrate,
            many = food.many
        )

        // Menggunakan coroutine untuk operasi asynchronous di viewModelScope
        viewModelScope.launch {
            repository.insert(foodEntity)
        }
    }

}