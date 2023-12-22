package com.example.calorietracker_uas.ui.main.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.calorietracker_uas.databinding.FragmentViewPagerBinding
import com.example.calorietracker_uas.ui.main.viewpager.screens.FirstScreenFragment
import com.example.calorietracker_uas.ui.main.viewpager.screens.SecondScreenFragment
import com.example.calorietracker_uas.ui.main.viewpager.screens.ThirdScreenFragment

class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf(
            FirstScreenFragment(),
            SecondScreenFragment(),
            ThirdScreenFragment()
        )

        val adapter =
            ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        binding.viewpager.adapter = adapter

    }
}