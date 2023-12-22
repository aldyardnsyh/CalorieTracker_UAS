package com.example.calorietracker_uas.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_table")
data class FoodEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val calorie: String,
    val url: String = "",
    val protein: String = "",
    val fat: String = "",
    val carbohydrate: String = "",
    var many: String = "1"
)