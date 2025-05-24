package com.example.foodappdacs.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodappdacs.adapter.CategoryAdapter
import com.example.foodappdacs.adapter.FoodAdapter
import com.example.foodappdacs.adapter.OnItemClickListener
import com.example.foodappdacs.databinding.FragmentHomeBinding
import com.example.foodappdacs.viewModel.CategoryViewModel
import com.example.foodappdacs.viewModel.FoodViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryVM : CategoryViewModel
    private lateinit var categoryAdapter : CategoryAdapter
    private lateinit var foodVM : FoodViewModel
    private lateinit var foodAdapter : FoodAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setUpRVCategory()
        //food
        setUpRVFood()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefreshLayout.isEnabled = false
        binding.searchView.setOnClickListener {

        }
    }

    private fun setUpRVCategory() {
        binding.rvFood.layoutManager =  GridLayoutManager(context, 2)
        categoryVM = ViewModelProvider(this)[CategoryViewModel::class.java]
        categoryAdapter = CategoryAdapter(emptyList())
        binding.rvCategory.adapter= categoryAdapter
        observeCategory()
        categoryVM.getCategories()
    }

    private fun setUpRVFood() {
        binding.rvFood.layoutManager =  LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        foodVM = ViewModelProvider(this)[FoodViewModel::class.java]
        foodAdapter = FoodAdapter(emptyList())
        binding.rvFood.adapter = foodAdapter
        observeFood()
        foodVM.getFoods()
        foodAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val foodId = foodVM.foods.value?.get(position)?.foodId
                var intent = Intent(context, FoodDetail::class.java)
                intent.putExtra("foodId", foodId)
                startActivity(intent)
            }
        }
        )
    }

    private fun observeFood() {
        foodVM.foods.observe(
            viewLifecycleOwner,
        ) {
            foodAdapter.setFoods(it)
        }
    }
    private fun observeCategory() {
        categoryVM.categories.observe(
            viewLifecycleOwner,
        ) {
            categoryAdapter.setCategories(it)
        }
    }

}