package com.example.calorietracker_uas.ui.main.food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker_uas.databinding.FragmentFoodsBinding
import com.example.calorietracker_uas.ui.main.MainActivity
import com.example.calorietracker_uas.ui.main.MainActivity.Companion.foodViewModelRoom
import com.example.calorietracker_uas.ui.main.addmeal.CustomFoodDialog

class FoodsFragment : Fragment() {

    private lateinit var binding: FragmentFoodsBinding
    private lateinit var rv: RecyclerView
    private lateinit var adapter: FoodAdapter
    private val foodViewModel by lazy { FoodViewModel(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
//                TODO("Not yet implemented")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        filterFoods(newText)
                    }
                    return true
            }

        })

        rv = binding.rvFood
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv.layoutManager = linearLayoutManager

//        foodObserver()
        foodObserverRoom()
        binding.btnCustomFood.setOnClickListener {
            val customFoodDialog = CustomFoodDialog(foodViewModelRoom)
            customFoodDialog.show(requireActivity().supportFragmentManager, "Custom Food Dialog")
        }
    }

//    private fun foodObserver() {
//        foodViewModel.foodList.observe(viewLifecycleOwner) { list ->
//            adapter = FoodAdapter(list)
//            rv.adapter = adapter
//        }
//    }

    private fun foodObserverRoom() {
        MainActivity.mFoodDao.getAllFoods().observe(viewLifecycleOwner) { listFood->
            adapter = FoodAdapter(listFood)
            rv.adapter = adapter
        }
    }

    private fun filterFoods(query: String) {
        MainActivity.mFoodDao.getFoodByName("%$query%").observe(viewLifecycleOwner) {
            adapter = FoodAdapter(it)
            rv.adapter = adapter

        }
    }


}