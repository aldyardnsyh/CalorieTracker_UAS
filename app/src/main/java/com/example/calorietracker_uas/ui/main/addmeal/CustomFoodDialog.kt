package com.example.calorietracker_uas.ui.main.addmeal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.calorietracker_uas.data.Food
import com.example.calorietracker_uas.data.room.CustomFoodViewModel
import com.example.calorietracker_uas.databinding.FoodDialogBinding

class CustomFoodDialog(private val foodViewModel: CustomFoodViewModel) : DialogFragment() {
    private lateinit var binding: FoodDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FoodDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etFoodName = binding.etFoodName
        val etFoodCalorie = binding.etFoodCalorie
        val btnAddFood = binding.btnAddFood

        btnAddFood.setOnClickListener {
            val foodName = etFoodName.text.toString()
            val calorie = etFoodCalorie.text.toString()

            if (foodName.isNotEmpty() && calorie.isNotEmpty()) {
                val customFood = Food(
                    name = foodName,
                    calorie = calorie,
                    // Tambahkan propertis lain sesuai kebutuhan
                )

                // Simpan atau kirim data ke ViewModel atau Repository sesuai kebutuhan
                // Misalnya, Anda dapat menggunakan foodViewModel untuk menyimpannya ke database atau repository
                foodViewModel.saveFoodToRoom(customFood)

                // Tutup dialog setelah menambahkan makanan kustom
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
            }
        }
    }
}