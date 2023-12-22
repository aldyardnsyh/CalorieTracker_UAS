package com.example.calorietracker_uas.ui.main.viewpager.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.calorietracker_uas.R
import com.example.calorietracker_uas.databinding.FragmentThirdScreenBinding
import com.example.calorietracker_uas.ui.main.profile.UserViewModel

class ThirdScreenFragment : Fragment() {

    private lateinit var binding: FragmentThirdScreenBinding
    private val viewModel by lazy { UserViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThirdScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.thirdNext.setOnClickListener {
            if (binding.etCalorieGoal.text.isNotEmpty()) {
                val calorieGoal = Integer.parseInt(binding.etCalorieGoal.text.toString())
                viewModel.updateSingleData(calorieGoal.toString(),"calorieGoal")
                it.findNavController().navigate(R.id.action_viewPagerFragment_to_mainFragment)
            } else {
                Toast.makeText(requireContext(), "Enter calorie goal first.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}