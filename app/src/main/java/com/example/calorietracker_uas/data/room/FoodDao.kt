package com.example.calorietracker_uas.data.room


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(food: FoodEntity)

    @Query("SELECT * FROM food_table")
    fun getAllFoods(): LiveData<List<FoodEntity>>

    @Query("Select * from food_table where name like :name")
    fun getFoodByName(name: String): LiveData<List<FoodEntity>>
}