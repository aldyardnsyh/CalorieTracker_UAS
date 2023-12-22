package com.example.calorietracker_uas.data.room

import androidx.lifecycle.LiveData

class FoodRepository(private val foodDao: FoodDao) {

    val allFoods: LiveData<List<FoodEntity>> = foodDao.getAllFoods()

    suspend fun insert(food: FoodEntity) {
        foodDao.insert(food)
    }

    suspend fun getAllFoods() = foodDao.getAllFoods()
}