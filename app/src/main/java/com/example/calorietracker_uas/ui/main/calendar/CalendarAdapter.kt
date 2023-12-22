package com.example.calorietracker_uas.ui.main.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker_uas.data.Food
import com.example.calorietracker_uas.databinding.CalendarCardBinding

class CalendarAdapter(private val calendarFoodList: List<Food>) :
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    var totalCalorie: Int = 0

    class ViewHolder(binding: CalendarCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val calendarCardBinding: CalendarCardBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CalendarCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = calendarFoodList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val calendar = calendarFoodList[position]

        val cardCalorie = Integer.parseInt(calendar.calorie)

        totalCalorie += cardCalorie
    }
}